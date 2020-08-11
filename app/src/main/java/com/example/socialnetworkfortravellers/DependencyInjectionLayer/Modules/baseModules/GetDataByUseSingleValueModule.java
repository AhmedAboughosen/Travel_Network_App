package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.GetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;

import dagger.Module;
import dagger.Provides;

@Module
public class GetDataByUseSingleValueModule {

    @Provides
    IGetDataByUseSingleValueService ProvidesGetDataByUseAddValueEventService(Context context) {
        return new GetDataByUseSingleValueService(context);
    }
}
