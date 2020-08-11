package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces.IAddTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.addTripSerivces.IAddTripServiceCallBack;

public class AddTripPresenter implements IAddTripPresenter {


    private IAddTripPresenterCallBack mAddTripPresenterCallBack;
    private IAddTripService mAddTripService;

    public AddTripPresenter(IAddTripService addTripService) {
        this.mAddTripService = addTripService;
        setUpAddTripServiceCallBack();

    }

    @Override
    public void setUpAddTripPresenterCallBack(IAddTripPresenterCallBack addTripPresenterCallBack) {
        this.mAddTripPresenterCallBack = addTripPresenterCallBack;
    }


    @Override
    public void addTrip(TripModel tripModel, String userKey) {
        mAddTripService.addTripContent(tripModel, userKey);
    }


    private void setUpAddTripServiceCallBack() {
        mAddTripService.setUpAddTripServiceCallBack(new IAddTripServiceCallBack() {
            @Override
            public void Successful() {
                mAddTripPresenterCallBack.onSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mAddTripPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddTripPresenterCallBack.networkErrorMessage();
            }


        });
    }

}
