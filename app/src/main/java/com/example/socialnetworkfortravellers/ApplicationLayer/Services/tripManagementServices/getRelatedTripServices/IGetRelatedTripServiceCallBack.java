package com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getRelatedTripServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.List;

public interface IGetRelatedTripServiceCallBack extends IBaseServiceCallBack {
    void getRelatedTrips(List<TripModel> tripModelList);

    void noRelatedTrips();

}
