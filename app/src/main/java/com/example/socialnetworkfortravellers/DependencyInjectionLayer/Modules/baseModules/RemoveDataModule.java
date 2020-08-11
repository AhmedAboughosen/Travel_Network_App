package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;

import dagger.Module;
import dagger.Provides;

@Module
public class RemoveDataModule {


    @Provides
    IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }
}
