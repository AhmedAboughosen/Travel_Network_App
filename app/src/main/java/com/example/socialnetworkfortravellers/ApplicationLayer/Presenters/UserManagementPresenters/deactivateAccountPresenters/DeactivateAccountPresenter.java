package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices.IRemoveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.removeUserInfoServices.IRemoveUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class DeactivateAccountPresenter implements IDeactivateAccountPresenter {


    private IBaseValidator mBaseValidator;
    private IReauthenticateAccountService mReauthenticateAccountService;
    private String  mPassword;
    private IDeactivateAccountPresenterCallBack mDeactivateAccountPresenterCallBack;
    private IRemoveUserInfoService mRemoveDataService;
    private String mCurrentUserID;


    public DeactivateAccountPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService, IRemoveUserInfoService removeUserInfoService) {
        this.mBaseValidator = baseValidator;
        this.mReauthenticateAccountService = reauthenticateAccountService;
        this.mRemoveDataService = removeUserInfoService;
        this.mCurrentUserID = CurrentUserIDUtil.getInstance().getCurrentUserID();
        setValidatorCallBack();
        setUpRemoveUserInfoServiceCallBack();
    }


    @Override
    public void deactivateAccount(String password) {
        this.mPassword = password;
        mBaseValidator.validation();
    }


    /**
     * call back if validation Succeeded or Failed.
     */
    private void setValidatorCallBack() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                setReauthenticateServiceCallBack();
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mDeactivateAccountPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void setReauthenticateServiceCallBack() {
        mReauthenticateAccountService.setReauthenticateServiceCallBack(new IReauthenticateAccountServiceCallBack() {
            @Override
            public void isSuccessful() {
                deactivateAccount();
            }

            @Override
            public void failure(String message) {

                mDeactivateAccountPresenterCallBack.failure(message);
            }
        });

        mReauthenticateAccountService.reauthenticate(mPassword);
    }


    /**
     * remove account
     */
    private void deactivateAccount() {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            user.delete()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            deactivateUserInfo();
                        } else {
                            mDeactivateAccountPresenterCallBack.failure(task.getException().getMessage());
                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void deactivateUserInfo() {
        mRemoveDataService.deactivateUserInfo(mCurrentUserID);
    }


    private void setUpRemoveUserInfoServiceCallBack(){
        mRemoveDataService.setUpRemoveUserInfoServiceCallBack(new IRemoveUserInfoServiceCallBack() {
            @Override
            public void removeUserInfoSuccessful() {
                mDeactivateAccountPresenterCallBack.removeUserDataSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mDeactivateAccountPresenterCallBack.failure(message);
            }

            @Override
            public void noInternetFound() {
                mDeactivateAccountPresenterCallBack.failure("there is no internet");
            }
        });
    }
    @Override
    public void setDeactivateAccountPresenterCallBack(IDeactivateAccountPresenterCallBack mDeactivateAccountPresenterCallBack) {
        this.mDeactivateAccountPresenterCallBack = mDeactivateAccountPresenterCallBack;
    }
}
