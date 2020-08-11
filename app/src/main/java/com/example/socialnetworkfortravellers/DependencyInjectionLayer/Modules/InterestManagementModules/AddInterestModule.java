package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.InterestManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters.AddInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters.IAddInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.RemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.ISaveUserRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices.SaveUserRelatedInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.ISaveUserInterestService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices.SaveUserInterestService;

import dagger.Module;
import dagger.Provides;

@Module
public class AddInterestModule {


    @Provides
    IAddInterestPresenter ProvidesAddInterestPresenter(ISaveUserInterestService saveUserInterestService, ISaveUserRelatedInterestService saveUserRelatedInterestService) {
        return new AddInterestPresenter(saveUserInterestService, saveUserRelatedInterestService);
    }

    @Provides
    ISaveUserInterestService ProvidesSaveUserInterestService(ISaveRawDataService saveRawDataService, IRemoveDataService removeDataService) {
        return new SaveUserInterestService(saveRawDataService, removeDataService);
    }

    @Provides
    IRemoveDataService ProvidesRemoveDataService() {
        return new RemoveDataService();
    }

    @Provides
    ISaveUserRelatedInterestService ProvidesSaveUserRelatedInterestService(ISaveRawDataService saveRawDataService) {
        return new SaveUserRelatedInterestService(saveRawDataService);
    }
}
