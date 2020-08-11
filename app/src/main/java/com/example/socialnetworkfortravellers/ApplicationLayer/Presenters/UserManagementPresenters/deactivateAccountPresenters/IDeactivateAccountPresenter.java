package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters;

public interface IDeactivateAccountPresenter {

    void deactivateAccount( String password);

    void setDeactivateAccountPresenterCallBack(IDeactivateAccountPresenterCallBack mDeactivateAccountPresenterCallBack);
}
