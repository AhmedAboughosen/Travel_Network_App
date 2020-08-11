package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices;

public interface IGetRelatedTripService {
    void getRelatedTrip(String countryName);

    void setGetRelatedTripServiceCallBack(IGetRelatedTripServiceCallBack mGetRelatedTripServiceCallBack);

}
