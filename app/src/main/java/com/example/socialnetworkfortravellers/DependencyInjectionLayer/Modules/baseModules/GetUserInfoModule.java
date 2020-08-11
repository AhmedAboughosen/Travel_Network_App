package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.GetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class GetUserInfoModule {


    @Provides
    IGetUserInfoService ProvidesGetUserInfoService(IGetDataByUseSingleValueService getDataService) {
        return new GetUserInfoService(getDataService);
    }
}
