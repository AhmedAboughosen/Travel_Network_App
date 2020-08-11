package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface IBioPresenterCallBack extends IPresenterCallBack {

    void saveBioSuccessful(String message);
    void errorInValidator(List<ValidationError> errors);
}
