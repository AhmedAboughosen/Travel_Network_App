package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

public interface IGetNotificationsService {

    void getNotifications(String userKey);
    void removeEventListener();
    void setUpGetNotificationsServiceCallback(IGetNotificationsServiceCallback getNotificationsServiceCallback);
}
