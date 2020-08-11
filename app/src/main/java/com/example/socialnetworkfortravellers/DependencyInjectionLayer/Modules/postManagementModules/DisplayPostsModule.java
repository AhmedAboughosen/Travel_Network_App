package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters.DisplayPostsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters.IDisplayPostsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters.GetPostKeyPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters.IGetPostKeyPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.ILikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.likeAndDisLikePresenters.LikeAndDisLikePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.GetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices.IGetPostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.GetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices.IGetPostOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;

import dagger.Module;
import dagger.Provides;

@Module
public class DisplayPostsModule {


    @Provides
    public IDisplayPostsPresenter ProvidesDisplayPostsPresenter(IGetPostOfUserService getPostOfUserService, IGetPostKeyPresenter getPostKeyPresenter, IGetUserInfoService getUserInfoService, ILikeAndDisLikePresenter likeAndDisLikePresenter) {
        return new DisplayPostsPresenter(getPostOfUserService, getPostKeyPresenter, getUserInfoService, likeAndDisLikePresenter);
    }

    @Provides
    public IGetPostOfUserService ProvidesGetPostOfUserService(IGetDataByUseAddValueEventService getDataByUseChildEventListenerService) {
        return new GetPostOfUserService(getDataByUseChildEventListenerService);
    }

    @Provides
    public IGetPostKeyPresenter ProvidesGetPostKeyPresenter(IGetPostKeyService getPostKeyService) {
        return new GetPostKeyPresenter(getPostKeyService);
    }

    @Provides
    public IGetPostKeyService ProvidesGetPostKeyService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetPostKeyService(getDataByUseSingleValueService);
    }

    @Provides
    public ILikeAndDisLikePresenter ProvidesLikeAndDisLikePresenter(IRemoveDataService removeDataService, ISaveRawDataService saveRawDataService) {
        return new LikeAndDisLikePresenter(removeDataService, saveRawDataService);
    }

    @Provides
    public IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }

}
