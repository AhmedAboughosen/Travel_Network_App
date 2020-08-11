package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.AccountModel;

public interface IRegisterPresenter {
    void createNewAccount(AccountModel accountModel);
    void setUpRegisterPresenterCallback(IRegisterPresenterCallback mRegisterPresenterCallback);
}
