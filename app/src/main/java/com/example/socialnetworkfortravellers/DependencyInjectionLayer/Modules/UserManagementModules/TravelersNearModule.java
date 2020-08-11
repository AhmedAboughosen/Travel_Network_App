package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IUserTrendingAndNearByPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.UserTrendingAndNearByPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.GetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.GetUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices.IGetUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.GetUserRatingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserRatingServices.IGetUserRatingService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class TravelersNearModule {

    public static final String GET_LIST_OF_USER_FOR_TRENDING_SERVICE = "GetListOfUserForTrendingService";
    public static final String GET_LIST_OF_USER_SERVICE = "GetListOfUserService";

    @Provides
    IUserTrendingAndNearByPresenter ProvidesNewsFeedPresenter(@Named(GET_LIST_OF_USER_SERVICE) IGetListOfUserService getListOfUserService, IGetUserCountryService getUserCountryService,
                                                              IGetUserRatingService getUserRatingService, @Named(GET_LIST_OF_USER_FOR_TRENDING_SERVICE) IGetListOfUserService getListOfUserForUserTrendingService) {
        return new UserTrendingAndNearByPresenter(getListOfUserService, getUserCountryService, getUserRatingService, getListOfUserForUserTrendingService);
    }


    @Provides
    @Named(GET_LIST_OF_USER_SERVICE)
    IGetListOfUserService ProvidesGetListOfUserService(IGetUserInfoService singleValueService) {
        return new GetListOfUserService(singleValueService);
    }

    @Provides
    @Named(GET_LIST_OF_USER_FOR_TRENDING_SERVICE)
    IGetListOfUserService ProvidesGetListOfUserForTrendingService(IGetUserInfoService singleValueService) {
        return new GetListOfUserService(singleValueService);
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
