package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters.IProfilePicturePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters.ProfilePicturePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.SaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.SaveProfileImageService;
import com.example.socialnetworkfortravellers.utilLayer.CompressImageUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class ProfilePictureModule {


    @Provides
    IProfilePicturePresenter ProvidesProfilePicturePresenter(ICompressImageUtil compressImageUtil, ISaveProfileImageService saveProfileImageService) {
        return new ProfilePicturePresenter(compressImageUtil, saveProfileImageService);
    }



    @Provides
    ISaveProfileImageService ProvidesSaveProfileImageService(ISaveImageService saveFileService, ISaveRawDataService saveRawDataService) {
        return new SaveProfileImageService(saveFileService, saveRawDataService);
    }

}
