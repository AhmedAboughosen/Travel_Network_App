package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules;

import android.app.Activity;
import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters.IMainPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters.MainPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.GetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.GetNewNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IGetNewNotificationsService;
import com.example.socialnetworkfortravellers.utilLayer.NotificationManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    @Provides
    IMainPresenter ProvidesMainPresenter(IGetNewNotificationsService getNotificationsService) {
        return new MainPresenter(getNotificationsService);
    }

    @Provides
    IGetNewNotificationsService ProvidesGetNewNotificationsService(IGetDataByUseChildEventListenerService getNotificationsService) {
        return new GetNewNotificationsService(getNotificationsService);
    }

    @Provides
    IGetDataByUseChildEventListenerService ProvidesGetDataByUseChildEventListenerService(Context context) {
        return new GetDataByUseChildEventListenerService(context);
    }

    @Provides
    NotificationManager ProvidesNotificationManager(Activity context) {
        return new NotificationManager(context);
    }
}
