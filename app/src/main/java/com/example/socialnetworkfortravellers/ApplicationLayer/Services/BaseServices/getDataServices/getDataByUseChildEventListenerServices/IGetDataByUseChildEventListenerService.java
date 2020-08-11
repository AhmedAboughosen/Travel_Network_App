package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices;

import com.google.firebase.database.DatabaseReference;

public interface IGetDataByUseChildEventListenerService {
    void setUpGetDataByUseChildEventListenerServiceCallBack(IGetDataByUseChildEventListenerServiceCallBack mGetDataByUseChildEventListenerServiceCallBack);

    void setUpDatabaseReference(DatabaseReference mDatabaseReference);

    void getData();

    void removeEventListener();
}
