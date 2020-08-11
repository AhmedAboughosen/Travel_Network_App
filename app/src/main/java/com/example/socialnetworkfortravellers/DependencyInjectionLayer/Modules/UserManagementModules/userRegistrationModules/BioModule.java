package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.UserManagementModules.userRegistrationModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters.BioPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters.IBioPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService.BioService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService.IBioService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Validators.baseValidators.IBaseValidator;

import dagger.Module;
import dagger.Provides;

@Module
public class BioModule {

    @Provides
    IBioPresenter ProvidesBioPresenter(IBaseValidator baseValidator, IBioService bioService) {
        return new BioPresenter(baseValidator, bioService);
    }

    @Provides
    IBioService ProvidesBioService(ISaveRawDataService saveRawDataService) {
        return new BioService(saveRawDataService);
    }

}
