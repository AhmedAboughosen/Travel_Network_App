package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.SaveRawDataService;

import dagger.Module;
import dagger.Provides;

@Module
public class SaveRawDataModule {

    @Provides
    ISaveRawDataService ProvidesSaveRawDataService(Context context) {
        return new SaveRawDataService(context);
    }
}
