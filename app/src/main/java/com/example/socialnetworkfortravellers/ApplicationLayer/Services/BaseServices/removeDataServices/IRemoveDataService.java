package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices;

import com.google.firebase.database.DatabaseReference;

public interface IRemoveDataService {
    void setUpDatabaseReference(DatabaseReference mDatabaseReference);

    void removeData();

    void setUpRemoveDataServiceCallBack(IRemoveDataServiceCallBack mRemoveDataServiceCallBack);
}
