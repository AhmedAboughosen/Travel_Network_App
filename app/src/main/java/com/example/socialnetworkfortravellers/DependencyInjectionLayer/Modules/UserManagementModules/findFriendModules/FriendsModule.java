package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.findFriendModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.FindFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IFindFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class FriendsModule {

    @Provides
    IFindFriendPresenter ProvidesFindFriendPresenter(IGetUserInfoService getUserInfoService) {
        return new FindFriendPresenter(getUserInfoService);
    }

}
