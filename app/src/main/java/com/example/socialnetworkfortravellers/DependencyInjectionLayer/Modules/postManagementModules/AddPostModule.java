package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;


import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters.AddPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.addPostPresenters.IAddPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices.AddPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices.IAddPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.SavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.UpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices.IUpdatePostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices.UpdatePostKeyService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.ISaveFileService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class AddPostModule {


    @Provides
    public IAddPostPresenter ProvidesAddPostPresenter(Context context, IAddPostService addPostService, ISavePostImageService savePostImageService, IUpdateNumberOfPostsService updateNumberOfPostsService
            , IUpdatePostKeyService updatePostKeyService) {
        return new AddPostPresenter(addPostService, savePostImageService, updateNumberOfPostsService, updatePostKeyService);
    }

    @Provides
    public IAddPostService ProvidesAddPostService(ISaveRawDataService saveRawDataService) {
        return new AddPostService(saveRawDataService);
    }


    @Provides
    public ISavePostImageService ProvidesSavePostImageService(ISaveImageService saveImageService, ICompressImageUtil compressImageUtil, ISaveRawDataService saveRawDataService ,ISaveFileService saveFileService) {
        return new SavePostImageService(saveImageService, compressImageUtil, saveRawDataService , saveFileService);
    }


    @Provides
    public IUpdateNumberOfPostsService ProvidesUpdateNumberOfPostsService(IUpdatePathUsingTransactionService updatePathUsingTransactionService) {
        return new UpdateNumberOfPostsService(updatePathUsingTransactionService);
    }


    @Provides
    public IUpdatePostKeyService ProvidesUpdatePostKeyService(ISaveRawDataService saveRawDataService) {
        return new UpdatePostKeyService(saveRawDataService);
    }
}
