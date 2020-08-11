package com.example.socialnetworkfortravellers.ViewLayer.Interfaces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;

public interface IOnTripItem {
    void onTripClick(TripModel tripModel , int image);
    void onDeleteTrip(String tripKey , int index);
}
