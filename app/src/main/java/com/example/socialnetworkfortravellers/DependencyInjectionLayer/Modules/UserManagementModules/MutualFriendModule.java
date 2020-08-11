package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters.IMutualFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters.MutualFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.GetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class MutualFriendModule {


    @Provides
    IMutualFriendPresenter ProvidesMutualFriendPresenter(IGetFriendsOfUserService getFollowingDataService, IGetUserInfoService getUserInfoService) {
        return new MutualFriendPresenter(getFollowingDataService, getUserInfoService);
    }

    @Provides
    IGetFriendsOfUserService ProvidesGetFriendsOfUserService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetFriendsOfUserService(getDataByUseSingleValueService);
    }
}
