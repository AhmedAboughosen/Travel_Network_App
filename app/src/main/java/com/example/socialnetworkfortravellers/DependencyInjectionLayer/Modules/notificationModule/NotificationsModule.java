package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.notificationModule;

import android.app.Activity;
import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters.INotificationPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters.NotificationPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.GetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.GetNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IGetNotificationsService;
import com.example.socialnetworkfortravellers.utilLayer.NotificationManager;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationsModule {


    @Provides
    INotificationPresenter ProvidesNotificationPresenter(IGetNotificationsService getNotificationsService) {
        return new NotificationPresenter(getNotificationsService);
    }


    @Provides
    IGetNotificationsService ProvidesGetNotificationsService(IGetDataByUseChildEventListenerService getDataByUseChildEventListenerService) {
        return new GetNotificationsService(getDataByUseChildEventListenerService);
    }

    @Provides
    IGetDataByUseChildEventListenerService ProvidesGetDataByUseChildEventListenerService(Context context) {
        return new GetDataByUseChildEventListenerService(context);
    }


}
