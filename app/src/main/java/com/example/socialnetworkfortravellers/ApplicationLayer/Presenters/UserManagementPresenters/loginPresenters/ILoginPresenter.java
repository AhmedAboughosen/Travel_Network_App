package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters;

public interface ILoginPresenter {

    void login(String email, String password);

    void removeEventListener();

    void setUpLoginPresenterCallBack(ILoginPresenterCallBack mLoginPresenterCallBack);
}
