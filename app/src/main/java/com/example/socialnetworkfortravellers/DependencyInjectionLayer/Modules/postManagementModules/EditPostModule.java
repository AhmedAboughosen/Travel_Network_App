package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.postManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters.EditPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.editPostPresenters.IEditPostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.SavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices.IUpdateImagePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices.UpdateImagePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices.IUpdatePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices.UpdatePostService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.ISaveFileService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class EditPostModule {


    @Provides
    public IEditPostPresenter ProvidesEditPostPresenter(IUpdatePostService postService, IUpdateImagePostService updateImagePostService) {
        return new EditPostPresenter(postService, updateImagePostService);
    }

    @Provides
    public IUpdatePostService ProvidesUpdatePostService(ISaveRawDataService saveRawDataService) {
        return new UpdatePostService(saveRawDataService);
    }

    @Provides
    public IUpdateImagePostService ProvidesUpdateImagePostService(ISavePostImageService savePostImageService, IRemoveDataService removeDataService) {
        return new UpdateImagePostService(savePostImageService, removeDataService);
    }


    @Provides
    public IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }


    @Provides
    public ISavePostImageService ProvidesSavePostImageService(ISaveImageService saveImageService, ICompressImageUtil compressImageUtil, ISaveRawDataService saveRawDataService, ISaveFileService saveFileService) {
        return new SavePostImageService(saveImageService, compressImageUtil, saveRawDataService, saveFileService);
    }


}
