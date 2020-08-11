package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IPostsNewsFeedPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.PostsNewsFeedPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.LikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.GetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.GetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.GetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices.IGetFriendsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.GetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class NewsFeedModule {


    public static final String GET_LIST_OF_USER_FOR_NEWS_FEED_SERVICE = "GetListOfUserForNewsFeedService";
    public static final String GET_POST_OF_USER_REFRESH_MODE_SERVICE = "GetPostOfUserRefreshModeService";
    public static final String GET_POST_OF_USER_SERVICE = "GetPostOfUserService";

    @Provides
    IPostsNewsFeedPresenter ProvidesPostsNewsFeedPresenter(IGetFriendsOfUserService getFriendsOfUserService, @Named(GET_LIST_OF_USER_FOR_NEWS_FEED_SERVICE) IGetListOfUserService getListOfUserForNewsFeedService, IGetPostKeyService getPostKeyService
            ,   @Named(GET_POST_OF_USER_SERVICE) IGetPostOfUserService getPostOfUserService,   @Named(GET_POST_OF_USER_REFRESH_MODE_SERVICE) IGetPostOfUserService getPostOfUserRefreshModeService , ILikeAndDisLikePresenter likeAndDisLikePresenter) {
        return new PostsNewsFeedPresenter(getFriendsOfUserService, getListOfUserForNewsFeedService, getPostKeyService, getPostOfUserService,getPostOfUserRefreshModeService,likeAndDisLikePresenter);
    }

    @Provides
    public ILikeAndDisLikePresenter ProvidesLikeAndDisLikePresenter(IRemoveDataService removeDataService, ISaveRawDataService saveRawDataService) {
        return new LikeAndDisLikePresenter(removeDataService, saveRawDataService);
    }

    @Provides
    public IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }


    @Provides
    IGetFriendsOfUserService ProvidesGetFriendsOfUserService(IGetDataByUseSingleValueService singleValueService) {
        return new GetFriendsOfUserService(singleValueService);
    }

    @Provides
    @Named(GET_LIST_OF_USER_FOR_NEWS_FEED_SERVICE)
    IGetListOfUserService ProvidesGetListOfUserForNewsFeedService(IGetUserInfoService singleValueService) {
        return new GetListOfUserService(singleValueService);
    }

    @Provides
    IGetPostKeyService ProvidesGetPostKeyService(IGetDataByUseSingleValueService singleValueService) {
        return new GetPostKeyService(singleValueService);
    }

    @Provides
    @Named(GET_POST_OF_USER_SERVICE)
    IGetPostOfUserService ProvidesGetPostOfUserService(IGetDataByUseAddValueEventService singleValueService) {
        return new GetPostOfUserService(singleValueService);
    }

    @Provides
    @Named(GET_POST_OF_USER_REFRESH_MODE_SERVICE)
    IGetPostOfUserService ProvidesGetPostOfUserRefreshModeService(IGetDataByUseAddValueEventService singleValueService) {
        return new GetPostOfUserService(singleValueService);
    }
}
