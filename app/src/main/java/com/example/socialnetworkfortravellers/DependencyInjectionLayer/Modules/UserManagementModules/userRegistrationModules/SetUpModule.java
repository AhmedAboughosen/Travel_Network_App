package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters.ISetUpPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters.SetUpPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.CountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.ISaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices.SaveUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class SetUpModule {


    @Provides
    ISetUpPresenter ProvidesSetUpPresenter(IBaseValidator baseValidator, ISaveUserInfoService saveInfoService, ICountriesListService countriesListService) {
        return new SetUpPresenter(baseValidator, saveInfoService, countriesListService);
    }

    @Provides
    ISaveUserInfoService ProvidesSetUpUserInfoService(ISaveRawDataService saveInfoService) {
        return new SaveUserInfoService(saveInfoService);
    }

    @Provides
    ICountriesListService ProvidesCountriesListService(Context context) {
        return new CountriesListService(context);
    }
}
