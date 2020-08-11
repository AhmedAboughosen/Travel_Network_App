package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.queriesGetDataServices;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public interface IQueriesGetDataService {
    void setUpDatabaseReference(Query mDatabaseReference);

    void getData();

    void setUpQueriesGetDataServiceCallBack(IQueriesGetDataServiceCallBack queriesGetDataServiceCallBack);
}
