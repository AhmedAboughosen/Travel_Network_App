package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices.IDeleteTripService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.deleteTripServices.IDeleteTripServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices.IGetUserTripsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.tripManagementServices.getUserTripsServices.IGetUserTripsServiceCallBack;

import java.util.List;

public class GetUserTripsPresenter implements IGetUserTripsPresenter {


    private IGetUserTripsPresenterCallBack mGetUserTripsPresenterCallBack;
    private IGetUserTripsService mGetUserTripsService;
    private IDeleteTripService mDeleteTripService;


    public GetUserTripsPresenter(IGetUserTripsService getUserTripsService, IDeleteTripService deleteTripService) {
        this.mGetUserTripsService = getUserTripsService;
        this.mDeleteTripService = deleteTripService;
        setGetUserTripServiceCallBack();
        setUpDeleteTripServiceCallBack();
    }


    @Override
    public void setUpGetUserTripsPresenterCallBack(IGetUserTripsPresenterCallBack getUserTripsPresenterCallBack) {
        this.mGetUserTripsPresenterCallBack = getUserTripsPresenterCallBack;
    }


    @Override
    public void geTrips(String userKey) {
        mGetUserTripsService.getTrip(userKey);
    }

    private void setGetUserTripServiceCallBack() {
        mGetUserTripsService.setGetUserTripServiceCallBack(new IGetUserTripsServiceCallBack() {
            @Override
            public void getAllUserTrips(List<TripModel> tripModelList) {
                mGetUserTripsPresenterCallBack.getAllUserTrips(tripModelList);
            }

            @Override
            public void noRelatedTrips() {
                mGetUserTripsPresenterCallBack.noRelatedTrips();
            }

            @Override
            public void showMessageError(String message) {
                mGetUserTripsPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mGetUserTripsPresenterCallBack.networkErrorMessage();
            }
        });
    }


    public void deleteTrip(String tripKey, String userKey) {
        mDeleteTripService.deleteTrip(tripKey, userKey);
    }

    private void setUpDeleteTripServiceCallBack() {
        mDeleteTripService.setUpDeleteTripServiceCallBack(new IDeleteTripServiceCallBack() {
            @Override
            public void failure(String message) {
                mGetUserTripsPresenterCallBack.showMessageError(message);
            }

            @Override
            public void isSuccessful() {
                mGetUserTripsPresenterCallBack.deleteTripSuccessful();
            }
        });
    }
}
