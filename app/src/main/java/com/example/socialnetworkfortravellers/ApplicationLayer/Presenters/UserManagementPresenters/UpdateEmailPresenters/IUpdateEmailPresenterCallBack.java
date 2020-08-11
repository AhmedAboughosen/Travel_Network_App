package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IUpdateEmailPresenterCallBack {

    void failure(String message);

    void errorInValidator(List<ValidationError> errors);

    void updateEmailSuccessful();

}
