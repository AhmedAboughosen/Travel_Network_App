package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters.DeletePostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.deletePostPresenters.IDeletePostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.DeleteImageOfPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.DeletePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeleteImageOfPostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices.IDeletePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.IUpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices.UpdateNumberOfPostsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;

import dagger.Module;
import dagger.Provides;

@Module
public class DeletePostModule {


    @Provides
    public IDeletePostPresenter ProvidesDeletePostPresenter(IDeletePostService deletePostService, IDeleteImageOfPostService deleteImageOfPostService) {
        return new DeletePostPresenter(deletePostService, deleteImageOfPostService);
    }

    @Provides
    public IDeletePostService ProvidesDeletePostService(IRemoveDataService removeDataService, IUpdateNumberOfPostsService updateNumberOfPostsService) {
        return new DeletePostService(removeDataService, updateNumberOfPostsService);
    }

    @Provides
    public IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }

    @Provides
    public IUpdateNumberOfPostsService ProvidesUpdateNumberOfPostsService(IUpdatePathUsingTransactionService updatePathUsingTransactionService) {
        return new UpdateNumberOfPostsService(updatePathUsingTransactionService);
    }


    @Provides
    public IDeleteImageOfPostService ProvidesDeleteImageOfPostService() {
        return new DeleteImageOfPostService();
    }
}
