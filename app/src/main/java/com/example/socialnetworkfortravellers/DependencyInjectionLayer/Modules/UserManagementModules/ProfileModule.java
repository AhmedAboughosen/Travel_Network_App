package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters.IProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters.ProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices.GetAllFollowersOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices.IGetAllFollowersOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices.GetAllFollowingsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices.IGetAllFollowingsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.GetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces.GetUserCountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces.IGetUserCountService;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfileModule {


    @Provides
    IProfilePresenter ProvidesUserProfilePresenter(IGetUserCountService getUserCounterSerivce, IGetUserInfoService getUserInfoService, IGetInterestOfUserService getInterestOfUserService, IGetAllFollowersOfUserService getAllFollowersOfUserService, IGetAllFollowingsOfUserService getAllFollowingsOfUserService) {

        return new ProfilePresenter(getUserCounterSerivce, getUserInfoService, getInterestOfUserService, getAllFollowersOfUserService, getAllFollowingsOfUserService);
    }

    @Provides
    IGetUserCountService ProvidesGetUserCounterSerivce(IGetDataByUseAddValueEventService getDataService) {
        return new GetUserCountService(getDataService);
    }

    @Provides
    IGetInterestOfUserService ProvidesGetInterestOfUserService(IGetDataByUseSingleValueService getDataService) {

        return new GetInterestOfUserService(getDataService);

    }

    @Provides
    IGetAllFollowersOfUserService ProvidesGetAllFollowersOfUserService(IGetDataByUseSingleValueService getDataService) {
        return new GetAllFollowersOfUserService(getDataService);
    }

    @Provides
    IGetAllFollowingsOfUserService ProvidesGetAllFollowingsOfUserService(IGetDataByUseSingleValueService getDataService) {
        return new GetAllFollowingsOfUserService(getDataService);

    }
}
