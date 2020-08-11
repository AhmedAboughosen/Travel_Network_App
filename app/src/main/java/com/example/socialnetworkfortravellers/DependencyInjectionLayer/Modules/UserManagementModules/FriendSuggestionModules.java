package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters.FriendSuggestionPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters.IFriendSuggestionPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.GetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices.GetRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices.IGetRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.GetUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.IGetUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.GetUserRatingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.IGetUserRatingService;

import dagger.Module;
import dagger.Provides;

@Module
public class FriendSuggestionModules {


    @Provides
    IFriendSuggestionPresenter ProvidesFriendSuggestionPresenter(IGetRelatedInterestService getRelatedInterestService, IGetInterestOfUserService getInterestOfUserService, IGetUserCountryService getUserCountryService
            , IGetUserRatingService getUserRatingService, IGetUserInfoService getUserInfoService) {
        return new FriendSuggestionPresenter(getRelatedInterestService, getInterestOfUserService, getUserCountryService, getUserRatingService, getUserInfoService);
    }


    @Provides
    IGetRelatedInterestService ProvidesGetRelatedInterestService(IGetDataByUseSingleValueService singleValueService) {
        return new GetRelatedInterestService(singleValueService);
    }

    @Provides
    IGetInterestOfUserService ProvidesGetInterestOfUserService(IGetDataByUseSingleValueService singleValueService) {
        return new GetInterestOfUserService(singleValueService);
    }


    @Provides
    IGetUserCountryService ProvidesGetUserCountryService(IGetDataByUseSingleValueService singleValueService) {
        return new GetUserCountryService(singleValueService);
    }


    @Provides
    IGetUserRatingService ProvidesGetUserRatingService(IGetDataByUseSingleValueService singleValueService) {
        return new GetUserRatingService(singleValueService);
    }

}
