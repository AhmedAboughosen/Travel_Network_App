package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.GetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;

import dagger.Module;
import dagger.Provides;

@Module
public class GetDataByUseAddValueEventModule {


    @Provides
    IGetDataByUseAddValueEventService ProvidesGetDataByUseAddValueEventService(Context context)
    {
        return new GetDataByUseAddValueEventService(context);
    }
}
