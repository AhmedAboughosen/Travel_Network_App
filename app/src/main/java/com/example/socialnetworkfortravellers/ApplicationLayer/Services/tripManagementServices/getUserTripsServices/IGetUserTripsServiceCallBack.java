package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.List;

public interface IGetUserTripsServiceCallBack extends IBaseServiceCallBack {
    void getAllUserTrips(List<TripModel> tripModelList);

    void noRelatedTrips();

}
