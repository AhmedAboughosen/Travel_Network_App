package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public interface IAddNotificationsService {

    void updateNotificationOfUser(NotificationsModel notificationsModel);
    void setUpAddNotificationsServiceCallback(IAddNotificationsServiceCallback addNotificationsServiceCallback);
}
