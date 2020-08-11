package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface ILoginPresenterCallBack extends IPresenterCallBack {
    void successCompleteSignInRequest(UserModel mUserModel);

    void errorInValidator(List<ValidationError> errors);

    void thisUserNotExists();

}
