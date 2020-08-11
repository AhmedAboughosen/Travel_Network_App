package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices.ILoginService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.loginServices.ILoginServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IValidatorCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;


public class LoginPresenter implements ILoginPresenter {


    private String mEmail, mPassword;
    private IBaseValidator mBaseValidator;
    private ILoginPresenterCallBack mLoginPresenterCallBack;
    private ILoginService mLoginService;
    private IGetUserInfoService mGetUserInfoService;

    public LoginPresenter(IBaseValidator baseValidator, ILoginService loginService, IGetUserInfoService getUserInfoService) {
        this.mBaseValidator = baseValidator;
        this.mGetUserInfoService = getUserInfoService;
        this.mLoginService = loginService;
        setUpValidatorCallback();
        setUpLoginServiceCallBack();
    }


    @Override
    public void login(String email, String password) {
        mEmail = email;
        mPassword = password;
        mBaseValidator.validation();
    }


    private void setUpValidatorCallback() {
        mBaseValidator.setValidatorCallBack(new IValidatorCallBack() {
            @Override
            public void onValidationSucceeded() {
                mLoginService.login(mEmail, mPassword);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                mLoginPresenterCallBack.errorInValidator(errors);
            }
        });
    }


    private void setUpLoginServiceCallBack() {
        mLoginService.setLoginServiceCallBack(new ILoginServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mLoginPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mLoginPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void Successful() {
                getUserInfo();
            }
        });
    }


    private void getUserInfo() {
        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                mLoginPresenterCallBack.successCompleteSignInRequest(mUserModel);
            }

            @Override
            public void showMessageError(String message) {
                mLoginPresenterCallBack.showMessageError(message);
            }

            @Override
            public void thisUserNotExists() {
                mLoginPresenterCallBack.thisUserNotExists();
            }

            @Override
            public void noInternetFound() {
                mLoginPresenterCallBack.networkErrorMessage();
            }
        });
        mGetUserInfoService.getUserInfo(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    @Override
    public void setUpLoginPresenterCallBack(ILoginPresenterCallBack mLoginPresenterCallBack) {
        this.mLoginPresenterCallBack = mLoginPresenterCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetUserInfoService.removeEventListener();
    }
}
