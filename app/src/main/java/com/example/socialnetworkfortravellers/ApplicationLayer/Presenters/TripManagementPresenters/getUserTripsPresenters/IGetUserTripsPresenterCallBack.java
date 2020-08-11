package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IGetUserTripsPresenterCallBack extends IPresenterCallBack {

    void noRelatedTrips();
    void deleteTripSuccessful();
    void getAllUserTrips(List<TripModel> tripModelList);
}
