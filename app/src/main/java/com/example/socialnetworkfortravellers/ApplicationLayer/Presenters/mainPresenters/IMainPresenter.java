package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters;

public interface IMainPresenter {
    void getNotifications(String userKey);
    void setUpMainPresenterCallback(IMainPresenterCallback mMainPresenterCallback);
    void removeEventListener();
}
