package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices;

import com.google.firebase.database.DatabaseReference;

public interface IGetDataByUseSingleValueService {
    void setUpDatabaseReference(DatabaseReference mDatabaseReference);

    void getData();

    void removeEventListener();

    void setUpGetDataServiceCallBack(IGetDataByUseSingleValueServiceCallBack mGetDataServiceCallBack);
}
