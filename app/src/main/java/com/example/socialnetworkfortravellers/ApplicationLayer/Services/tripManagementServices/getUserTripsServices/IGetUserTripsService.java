package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices;

public interface IGetUserTripsService {
    void getTrip(String userKey);

    void setGetUserTripServiceCallBack(IGetUserTripsServiceCallBack mGetRelatedTripServiceCallBack);

}
