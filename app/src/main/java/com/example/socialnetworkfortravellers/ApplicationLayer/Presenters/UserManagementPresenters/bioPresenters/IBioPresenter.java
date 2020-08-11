package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters;

public interface IBioPresenter {
    void saveBio(String bio);

    void setUpBioPresenterCallBack(IBioPresenterCallBack mBioPresenterCallBack);
}
