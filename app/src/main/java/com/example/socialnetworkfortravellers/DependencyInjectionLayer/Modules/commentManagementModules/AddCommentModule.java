package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.commentManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters.AddCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters.IAddCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices.AddCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices.IAddCommentService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices.AddRepliesService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices.IAddRepliesService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class AddCommentModule {


    @Provides
    public IAddCommentPresenter ProvidesAddCommentPresenter(IAddCommentService addCommentService, IAddRepliesService repliesService) {
        return new AddCommentPresenter(addCommentService, repliesService);
    }

    @Provides
    public IAddCommentService ProvidesAddCommentService(ISaveImageService saveImageService, ICompressImageUtil compressImageUtil, ISaveRawDataService saveRawDataService, IUpdatePathUsingTransactionService updatePathUsingTransactionService) {
        return new AddCommentService(saveImageService, compressImageUtil, saveRawDataService, updatePathUsingTransactionService);
    }

    @Provides
    public IAddRepliesService ProvidesAddRepliesService(ISaveRawDataService saveRawDataService) {
        return new AddRepliesService(saveRawDataService);
    }
}
