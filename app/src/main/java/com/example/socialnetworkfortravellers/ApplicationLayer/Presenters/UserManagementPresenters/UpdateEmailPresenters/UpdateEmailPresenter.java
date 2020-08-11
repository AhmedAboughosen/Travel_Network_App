package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.reauthenticateAccountServices.IReauthenticateAccountServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class UpdateEmailPresenter implements IUpdateEmailPresenter {


    private IBaseValidator mBaseValidator;
    private IReauthenticateAccountService mReauthenticateAccountService;
    private String mNewEmail, mPassword;
    private IUpdateEmailPresenterCallBack mUpdateEmailPresenterCallBack;


    public UpdateEmailPresenter(IBaseValidator baseValidator, IReauthenticateAccountService reauthenticateAccountService) {
        this.mBaseValidator = baseValidator;
        this.mReauthenticateAccountService = reauthenticateAccountService;
        setValidatorCallBack();
    }


    @Override
    public void updateEmail(String newEmail, String password) {

        this.mNewEmail = newEmail;
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
                mUpdateEmailPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void setReauthenticateServiceCallBack() {
        mReauthenticateAccountService.setReauthenticateServiceCallBack(new IReauthenticateAccountServiceCallBack() {
            @Override
            public void isSuccessful() {
                updateEmailAccount();
            }

            @Override
            public void failure(String message) {

                mUpdateEmailPresenterCallBack.failure(message);
            }
        });

        mReauthenticateAccountService.reauthenticate(mPassword);
    }


    /**
     * update user Email account
     */
    private void updateEmailAccount() {
        try {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            assert user != null;
            user.updateEmail(mNewEmail)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            mUpdateEmailPresenterCallBack.updateEmailSuccessful();
                        } else {
                            mUpdateEmailPresenterCallBack.failure(task.getException().getMessage());
                        }
                    });

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void setUpdateEmailPresenterCallBack(IUpdateEmailPresenterCallBack mUpdateEmailPresenterCallBack) {
        this.mUpdateEmailPresenterCallBack = mUpdateEmailPresenterCallBack;
    }
}
