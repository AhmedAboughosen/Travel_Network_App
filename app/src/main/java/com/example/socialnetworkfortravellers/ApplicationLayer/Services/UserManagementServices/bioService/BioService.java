package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.bioService;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BioService implements IBioService {


    private ISaveRawDataService mSaveRawDataService;
    private IBioServiceCallBack mBioServiceCallBack;

    public BioService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }

    @Override
    public void saveBio(String bio) {

        DatabaseReference mBioUsersRef = FirebaseDatabase.getInstance().getReference().child(UserNode.USER).child(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mSaveRawDataService.setDatabaseReference(mBioUsersRef);
        HashMap<String, Object> mBioMap = new HashMap<>();
        mBioMap.put(UserNode.BIO, bio);
        mSaveRawDataService.setMapData(mBioMap);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mBioServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mBioServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                mBioServiceCallBack.Successful("Bio Uploaded successfully");
            }

        });

        mSaveRawDataService.updateData();
    }

    @Override
    public void setUpBioServiceCallBack(IBioServiceCallBack mBioServiceCallBack) {
        this.mBioServiceCallBack = mBioServiceCallBack;
    }
}
