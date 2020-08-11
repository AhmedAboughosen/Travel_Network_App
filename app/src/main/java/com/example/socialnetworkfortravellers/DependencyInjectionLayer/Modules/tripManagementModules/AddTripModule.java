package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters.AddTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters.IAddTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces.AddTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces.IAddTripService;

import dagger.Module;
import dagger.Provides;

@Module
public class AddTripModule {

    @Provides
    IAddTripPresenter ProvidesAddTripPresenter(IAddTripService addTripService) {
        return new AddTripPresenter(addTripService);
    }

    @Provides
    IAddTripService ProvidesAddTripService(ISaveRawDataService saveRawDataService) {
        return new AddTripService(saveRawDataService);
    }
}
