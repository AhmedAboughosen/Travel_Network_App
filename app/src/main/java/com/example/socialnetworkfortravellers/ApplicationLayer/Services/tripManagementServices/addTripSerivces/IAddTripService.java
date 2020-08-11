package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;

public interface IAddTripService {
    void setUpAddTripServiceCallBack(IAddTripServiceCallBack addTripServiceCallBack);

    void addTripContent(TripModel tripModel, String userKey);

}
