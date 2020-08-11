package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters.GetUserTripsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters.IGetUserTripsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices.DeleteTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices.IDeleteTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices.GetUserTripsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices.IGetUserTripsService;

import dagger.Module;
import dagger.Provides;

@Module
public class GetTripsModule {

    @Provides
    IGetUserTripsPresenter ProvidesGetUserTripsPresenter(IGetUserTripsService getUserTripsService , IDeleteTripService deleteTripService) {
        return new GetUserTripsPresenter(getUserTripsService,deleteTripService);
    }

    @Provides
    IGetUserTripsService ProvidesGetUserTripsService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetUserTripsService(getDataByUseSingleValueService);
    }

    @Provides
    IDeleteTripService ProvidesDeleteTripService(IRemoveDataService removeDataService) {
        return new DeleteTripService(removeDataService);
    }
}
