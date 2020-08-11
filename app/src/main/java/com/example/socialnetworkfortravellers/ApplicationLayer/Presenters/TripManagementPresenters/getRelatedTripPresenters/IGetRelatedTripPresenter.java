package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters;

public interface IGetRelatedTripPresenter {

    void getRelatedTrip(String from, String to , String countryName);

    void setUpGetRelatedTripPresenterCallBack(IGetRelatedTripPresenterCallBack addTripPresenterCallBack);
}
