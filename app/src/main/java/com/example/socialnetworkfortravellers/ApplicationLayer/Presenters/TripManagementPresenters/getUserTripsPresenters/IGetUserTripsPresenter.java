package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters;

public interface IGetUserTripsPresenter {

    void geTrips(String userKey);
    void deleteTrip(String tripKey, String userKey);
    void setUpGetUserTripsPresenterCallBack(IGetUserTripsPresenterCallBack getUserTripsPresenterCallBack);
}
