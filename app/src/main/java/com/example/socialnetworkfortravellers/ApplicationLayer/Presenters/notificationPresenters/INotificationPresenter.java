package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters;

public interface INotificationPresenter {

    void getNotifications(String userKey);
    void removeEventListener();
    void setUpGetNotificationsPresenterCallback(IGetNotificationsPresenterCallback getNotificationsPresenterCallback);
}
