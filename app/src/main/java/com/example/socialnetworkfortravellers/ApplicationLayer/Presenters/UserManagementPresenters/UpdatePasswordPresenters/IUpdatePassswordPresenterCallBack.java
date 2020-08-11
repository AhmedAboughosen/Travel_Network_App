package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters;

import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IUpdatePassswordPresenterCallBack {

    void failure(String message);

    void errorInValidator(List<ValidationError> errors);

    void updatePasswordSuccessful();

}
