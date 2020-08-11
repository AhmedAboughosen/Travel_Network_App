package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IRegisterPresenterCallback extends IPresenterCallBack {

    void successCompleteSignInRequest();

    void errorInValidator(List<ValidationError> errors);
}
