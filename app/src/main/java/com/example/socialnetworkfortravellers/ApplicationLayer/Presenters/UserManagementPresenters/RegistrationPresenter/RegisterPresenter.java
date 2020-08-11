package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.AccountModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService.ICreateUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.createUserService.ICreateUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

/**
 * responsibility of this this class used to handel logic of create account.
 */
public class RegisterPresenter implements IRegisterPresenter {


    private AccountModel mAccountModel;
    private IBaseValidator mBaseValidator;
    private IRegisterPresenterCallback mRegisterPresenterCallback;
    private ICreateUserService mCreateUserService;

    public RegisterPresenter(IBaseValidator baseValidator, ICreateUserService createUserPresenter) {
        this.mCreateUserService = createUserPresenter;
        this.mBaseValidator = baseValidator;
        setUpValidatorCallback();
        setUpCreateUserPresenterCallBack();
    }


    /**
     * first check if email and password is correct then call Service to create a new user
     *
     * @param accountModel
     */
    @Override
    public void createNewAccount(AccountModel accountModel) {
        this.mAccountModel = accountModel;
        mBaseValidator.validation();
    }


    private void setUpValidatorCallback() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            /**
             *  call Service to create a new user
             */
            @Override
            public void onValidationSucceeded() {
                mCreateUserService.createNewAccount(mAccountModel);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mRegisterPresenterCallback.errorInValidator(errors);
            }
        });
    }

    /**
     * setUpCreateUserPresenterCallBack
     */
    private void setUpCreateUserPresenterCallBack() {
        mCreateUserService.setUpCreateUserServiceCallBack(new ICreateUserServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mRegisterPresenterCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mRegisterPresenterCallback.networkErrorMessage();
            }

            @Override
            public void Successful() {
                mRegisterPresenterCallback.successCompleteSignInRequest();
            }
        });
    }


    @Override
    public void setUpRegisterPresenterCallback(IRegisterPresenterCallback mRegisterPresenterCallback) {
        this.mRegisterPresenterCallback = mRegisterPresenterCallback;
    }
}
