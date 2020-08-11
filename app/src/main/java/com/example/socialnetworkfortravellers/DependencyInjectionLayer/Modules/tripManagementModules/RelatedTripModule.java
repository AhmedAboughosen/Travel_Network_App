package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters.GetRelatedTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters.IGetRelatedTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices.GetRelatedTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices.IGetRelatedTripService;

import dagger.Module;
import dagger.Provides;

@Module
public class RelatedTripModule {

    @Provides
    IGetRelatedTripPresenter ProvidesGetRelatedTripPresenter(IGetRelatedTripService getRelatedTripService) {
        return new GetRelatedTripPresenter(getRelatedTripService);
    }

    @Provides
    IGetRelatedTripService ProvidesGetRelatedTripService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetRelatedTripService(getDataByUseSingleValueService);
    }

}
