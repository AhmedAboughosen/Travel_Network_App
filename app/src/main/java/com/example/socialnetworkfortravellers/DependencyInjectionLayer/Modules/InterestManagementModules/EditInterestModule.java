package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.InterestManagementModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters.EditInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters.IEditInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.GetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
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
public class EditInterestModule {


    @Provides
    IEditInterestPresenter ProvidesAddInterestPresenter(IGetInterestOfUserService getInterestOfUserService, ISaveUserInterestService saveUserInterestService, ISaveUserRelatedInterestService saveUserRelatedInterestService) {
        return new EditInterestPresenter(getInterestOfUserService, saveUserInterestService, saveUserRelatedInterestService);
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

    @Provides
    IGetInterestOfUserService ProvidesGetInterestOfUserService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetInterestOfUserService(getDataByUseSingleValueService);
    }
}
