package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters.EditUserInformationPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters.IEditUserInformationPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.SaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.CountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.SaveProfileImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.SaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService.IRemoveUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService.RemoveUserCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;
import com.example.socialnetworkfortravellers.utilLayer.CompressImageUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class EditUserInformationModule {

    @Provides
    IEditUserInformationPresenter ProvidesEditUserInformationPresenter(ISaveUserInfoService saveUserInfoService, ISaveProfileImageService saveProfileImageService, IBaseValidator baseValidator,
                                                                       ICountriesListService countriesListService, IRemoveUserCountryService removeUserCountryService, ICompressImageUtil compressImageUtil) {
        return new EditUserInformationPresenter(saveUserInfoService, saveProfileImageService, baseValidator, countriesListService, removeUserCountryService, compressImageUtil);
    }


    @Provides
    ISaveUserInfoService ProvidesSaveUserInfoService(ISaveRawDataService mSaveRawDataService) {
        return new SaveUserInfoService(mSaveRawDataService);
    }

    @Provides
    ISaveProfileImageService ProvidesProfileImageService(ISaveImageService saveFileService, ISaveRawDataService saveRawDataService) {
        return new SaveProfileImageService(saveFileService, saveRawDataService);
    }


    @Provides
    ICountriesListService ProvidesCountriesListService(Context mContext) {
        return new CountriesListService(mContext);
    }

    @Provides
    IRemoveUserCountryService ProvidesRemoveUserCountryService(IRemoveDataService removeDataService) {
        return new RemoveUserCountryService(removeDataService);
    }

    @Provides
    IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }

}
