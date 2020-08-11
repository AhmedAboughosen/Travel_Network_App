package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.KEYS_POST;

public class UpdatePostKeyService implements IUpdatePostKeyService {


    private ISaveRawDataService mSaveRawDataService;
    private IUpdatePostKeyServiceCallBack mUpdatePostKeyServiceCallBack;

    public UpdatePostKeyService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    @Override
    public void addNewPostKey(Object timestamp, String postKey, String userKey) {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child(KEYS_POST).child(userKey);
        HashMap<String, Object> postKeyMap = new HashMap<>();
        postKeyMap.put(postKey, timestamp);

        mSaveRawDataService.setMapData(postKeyMap);
        mSaveRawDataService.setDatabaseReference(postsRef);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mUpdatePostKeyServiceCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mUpdatePostKeyServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                mUpdatePostKeyServiceCallBack.addPostSuccessful();
            }

        });
        mSaveRawDataService.updateData();

    }

    @Override
    public void setUpdatePostKeyServiceCallBack(IUpdatePostKeyServiceCallBack mUpdatePostKeyServiceCallBack) {
        this.mUpdatePostKeyServiceCallBack = mUpdatePostKeyServiceCallBack;
    }
}
