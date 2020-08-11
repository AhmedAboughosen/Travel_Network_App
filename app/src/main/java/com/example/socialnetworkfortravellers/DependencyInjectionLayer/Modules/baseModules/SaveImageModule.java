package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.ISaveFileService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.SaveFileService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.SaveImageService;

import dagger.Module;
import dagger.Provides;

@Module
public class SaveImageModule {


    @Provides
    public ISaveFileService ProvidesSaveFileService() {
        return new SaveFileService();
    }

    @Provides
    public ISaveImageService ProvidesSaveImageService(Context  context) {
        return new SaveImageService(context);
    }
}
