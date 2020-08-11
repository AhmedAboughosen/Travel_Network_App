package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IForgetAccountPresenterCallBack extends IPresenterCallBack {

    void sendPasswordSuccessful();


    void onValidationFailed(List<ValidationError> errors);


}
