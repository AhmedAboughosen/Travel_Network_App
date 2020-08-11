package com.example.socialnetworkfortravellers.eventBus;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public class ListOfNotificationsEvent {

    private NotificationsModel list;

    public ListOfNotificationsEvent(NotificationsModel notificationsModels) {
        this.list = notificationsModels;
    }

    public NotificationsModel getList() {
        return list;
    }
}
