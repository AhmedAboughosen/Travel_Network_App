package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IGetRelatedTripPresenterCallBack extends IPresenterCallBack {

    void noRelatedTrips();
    void getRelatedUserSuccessful(List<String> list_users);
}
