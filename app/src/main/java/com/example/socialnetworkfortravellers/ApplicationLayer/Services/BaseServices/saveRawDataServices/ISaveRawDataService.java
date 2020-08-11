package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public interface ISaveRawDataService  {
    void updateData();
    void setDatabaseReference(DatabaseReference mDatabaseReference);
    void setMapData(HashMap<String, Object> mMapData);
    void setUpSaveInfoServiceCallBack(ISaveRawDataServiceCallBack saveInfoServiceCallBack);
}
