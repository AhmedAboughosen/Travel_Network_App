package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.mobsandgeeks.saripaar.ValidationError;

import java.util.List;

public interface ISetUpPresenterCallBack extends IPresenterCallBack {
    void errorInValidator(List<ValidationError> errors);

    void completeSaveDataSuccessfully();

    void getAllCountry(List<CountryModel> list);

    void noCountry(String str);


}
