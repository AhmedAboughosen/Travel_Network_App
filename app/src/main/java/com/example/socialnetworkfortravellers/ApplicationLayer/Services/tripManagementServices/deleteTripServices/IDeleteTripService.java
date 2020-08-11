package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices;

public interface IDeleteTripService {
    void deleteTrip(String tripKey, String userKey);

    void setUpDeleteTripServiceCallBack(IDeleteTripServiceCallBack mDeletePostServiceCallBack);

}
