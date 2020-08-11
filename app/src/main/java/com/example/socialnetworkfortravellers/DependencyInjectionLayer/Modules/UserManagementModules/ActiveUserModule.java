package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters.GetListOfUserPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters.IGetListOfUserPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.GetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class ActiveUserModule {


    @Provides
    IGetListOfUserPresenter ProvidesGetListOfUserPresenter(IGetListOfUserService getListOfUserService) {
        return new GetListOfUserPresenter(getListOfUserService);
    }

    @Provides
    IGetListOfUserService ProvidesGetListOfUserService(IGetUserInfoService getUserInfoService) {
        return new GetListOfUserService(getUserInfoService);
    }
}
