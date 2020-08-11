package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.commentManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters.ILikeAndDislikeToCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters.LikeAndDislikeToCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService.AddDisLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService.IAddDisLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices.AddLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices.IAddLikeToCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;

import dagger.Module;
import dagger.Provides;

@Module
public class LikeAndDisLikeModule {

    @Provides
    public ILikeAndDislikeToCommentPresenter ProvidesLikeAndDislikeToCommentPresenter(IAddLikeToCommentService addLikeToCommentService, IAddDisLikeToCommentService addDisLikeToCommentService) {
        return new LikeAndDislikeToCommentPresenter(addLikeToCommentService, addDisLikeToCommentService);
    }


    @Provides
    public IAddLikeToCommentService ProvidesAddLikeToCommentService(ISaveRawDataService saveRawDataService) {
        return new AddLikeToCommentService(saveRawDataService);
    }


    @Provides
    public IAddDisLikeToCommentService ProvidesAddDisLikeToCommentService(IRemoveDataService removeDataService) {
        return new AddDisLikeToCommentService(removeDataService);
    }
}
