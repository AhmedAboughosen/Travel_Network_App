package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters;

public interface IUpdateEmailPresenter {

    void updateEmail(String newEmail, String password);

    void setUpdateEmailPresenterCallBack(IUpdateEmailPresenterCallBack mUpdateEmailPresenterCallBack);
}
