package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.GetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;

import dagger.Module;
import dagger.Provides;

@Module
public class GetDataByUseChildEventModule {

    @Provides
    public IGetDataByUseChildEventListenerService ProvidesGetDataByUseChildEventListenerService(Context context) {

        return new GetDataByUseChildEventListenerService(context);
    }
}
