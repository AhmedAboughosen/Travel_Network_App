package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices.IForgetAccountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.forgetAccountServices.IForgetAccountServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public class ForgetAccountPresenter implements IForgetAccountPresenter {

    private IBaseValidator mBaseValidator;
    private IForgetAccountPresenterCallBack mForgetAccountPresenterCallBack;
    private String mEmailAddress;
    private IForgetAccountService mForgetAccountService;

    public ForgetAccountPresenter(IBaseValidator baseValidator, IForgetAccountService forgetAccountService) {
        this.mBaseValidator = baseValidator;
        this.mForgetAccountService = forgetAccountService;
        setUpForgetAccountServiceCallBack();
        setValidatorCallBack();
    }


    private void setValidatorCallBack() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                mForgetAccountService.sendPasswordResetEmail(mEmailAddress);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mForgetAccountPresenterCallBack.onValidationFailed(errors);
            }
        });
    }

    private void setUpForgetAccountServiceCallBack() {
        mForgetAccountService.setUpForgetAccountServiceCallBack(new IForgetAccountServiceCallBack() {

            @Override
            public void sendPasswordSuccessful() {
                mForgetAccountPresenterCallBack.sendPasswordSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mForgetAccountPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mForgetAccountPresenterCallBack.networkErrorMessage();
            }
        });
    }

    /**
     * send post ot email which user enter
     */
    @Override
    public void sendPasswordResetEmail(String emailAddress) {

        this.mEmailAddress = emailAddress;
        this.mBaseValidator.validation();

    }


    @Override
    public void setUpForgetAccountPresenterCallBack(IForgetAccountPresenterCallBack mForgetAccountPresenterCallBack) {
        this.mForgetAccountPresenterCallBack = mForgetAccountPresenterCallBack;
    }
}
