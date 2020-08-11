package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters;

public interface IUpdatePassswordPresenter {

    void updatePassword(String currentPassword, String password);

    void setUpdatePasswordPresenterCallBack(IUpdatePassswordPresenterCallBack mUpdatePassswordPresenterCallBack);
}
