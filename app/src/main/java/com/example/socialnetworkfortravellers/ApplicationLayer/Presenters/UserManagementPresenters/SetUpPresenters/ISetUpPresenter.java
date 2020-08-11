package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices.IGetAllCountryServicesCallBack;

public interface ISetUpPresenter {

    void getAllCountry();

    void setUpNewAccount(UserModel userModel);

    void setUpSetUpPresenterCallBack(ISetUpPresenterCallBack setUpPresenterCallBack);

}
