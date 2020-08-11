package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public interface IGetNotificationsPresenterCallback extends IPresenterCallBack {
    void onGetNotifications(NotificationsModel notificationsModel);
    void noNotifications();
}
