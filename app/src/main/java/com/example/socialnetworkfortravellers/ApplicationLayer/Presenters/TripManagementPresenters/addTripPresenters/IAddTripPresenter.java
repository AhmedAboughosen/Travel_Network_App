package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;

public interface IAddTripPresenter {

    void addTrip(TripModel tripModel, String userKey);


    void setUpAddTripPresenterCallBack(IAddTripPresenterCallBack addTripPresenterCallBack);
}
