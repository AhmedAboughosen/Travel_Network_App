package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters;

public interface IForgetAccountPresenter {
    void setUpForgetAccountPresenterCallBack(IForgetAccountPresenterCallBack mForgetAccountPresenterCallBack);

    void sendPasswordResetEmail(String emailAddress);

}
