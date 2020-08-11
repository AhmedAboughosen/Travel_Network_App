package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.findFriendModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.GetFullNameOfUsersPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IGetFullNameOfUsersPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices.GetFullNameOfUsersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices.IGetFullNameOfUsersService;

import dagger.Module;
import dagger.Provides;

@Module
public class FindFriendModule {

    @Provides
    IGetFullNameOfUsersPresenter ProvidesGetFullNameOfUsersPresenter(IGetFullNameOfUsersService getDataService) {
        return new GetFullNameOfUsersPresenter(getDataService);
    }

    @Provides
    IGetFullNameOfUsersService ProvidesGetFullNameOfUsersService(IGetDataByUseSingleValueService getDataService) {
        return new GetFullNameOfUsersService(getDataService);
    }

}
