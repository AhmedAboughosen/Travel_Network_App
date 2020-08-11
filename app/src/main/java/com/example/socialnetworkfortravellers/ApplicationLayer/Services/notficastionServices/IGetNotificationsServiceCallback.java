package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IGetNotificationsServiceCallback extends IBaseServiceCallBack {

    void onGetNotifications(NotificationsModel notificationsModel);

    void noNotifications();
}
