package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IDeactivateAccountPresenterCallBack {

    void failure(String message);

    void errorInValidator(List<ValidationError> errors);

    void removeUserDataSuccessful();

}
