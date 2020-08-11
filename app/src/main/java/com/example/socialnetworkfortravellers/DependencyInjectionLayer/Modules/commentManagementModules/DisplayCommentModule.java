package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.commentManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters.DisplayCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters.IDisplayCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices.GetAllCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices.IGetAllCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;

import dagger.Module;
import dagger.Provides;

@Module
public class DisplayCommentModule {

    @Provides
    public IDisplayCommentPresenter ProvidesDisPlayCommentPresenter(IGetAllCommentService getAllCommentService , IGetUserInfoService getUserInfoService) {
        return new DisplayCommentPresenter(getAllCommentService,getUserInfoService);
    }

    @Provides
    public IGetAllCommentService ProvidesGetAllCommentService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetAllCommentService(getDataByUseSingleValueService);
    }
}
