package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class UpdatePassswordPresenter implements IUpdatePassswordPresenter {


    private IBaseValidator mBaseValidator;
    private IReauthenticateAccountService mReauthenticateAccountService;
    private String mCurrentPassword, mNewPassword;
    private IUpdatePassswordPresenterCallBack mUpdatePassswordPresenterCallBack;


    public UpdatePassswordPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService) {
        this.mBaseValidator = baseValidator;
        this.mReauthenticateAccountService = reauthenticateAccountService;
        setValidatorCallBack();
    }


    @Override
    public void updatePassword(String current_Password, String password) {

        this.mCurrentPassword = current_Password;
        this.mNewPassword = password;
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
                mUpdatePassswordPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void setReauthenticateServiceCallBack() {
        mReauthenticateAccountService.setReauthenticateServiceCallBack(new IReauthenticateAccountServiceCallBack() {
            @Override
            public void isSuccessful() {
                updatePasswordAccount(mNewPassword);
            }

            @Override
            public void failure(String message) {

                mUpdatePassswordPresenterCallBack.failure(message);
            }
        });

        mReauthenticateAccountService.reauthenticate(mCurrentPassword);
    }


    /**
     * update user Email account
     */
    private void updatePasswordAccount(String newPassword) {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            user.updatePassword(newPassword)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mUpdatePassswordPresenterCallBack.updatePasswordSuccessful();
                        } else {
                            mUpdatePassswordPresenterCallBack.failure(task.getException().getMessage());
                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setUpdatePasswordPresenterCallBack(IUpdatePassswordPresenterCallBack mUpdatePassswordPresenterCallBack) {
        this.mUpdatePassswordPresenterCallBack = mUpdatePassswordPresenterCallBack;
    }
}
