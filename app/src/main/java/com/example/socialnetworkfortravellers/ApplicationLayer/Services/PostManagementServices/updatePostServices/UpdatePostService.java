package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class UpdatePostService implements IUpdatePostService {


    private ISaveRawDataService mSaveRawDataService;
    private HashMap<String, Object> postContent;
    private IUpdatePostServiceCallBack mUpdatePostServiceCallBack;

    public UpdatePostService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
        postContent = new HashMap<>();
        setUpSaveInfoServiceCallBack();

    }


    @Override
    public void updatePostContent(String userKey, String postKey) {
        DatabaseReference PostsRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(userKey).child(postKey);

        mSaveRawDataService.setDatabaseReference(PostsRef);
        mSaveRawDataService.setMapData(postContent);
        mSaveRawDataService.updateData();
    }


    private void setUpSaveInfoServiceCallBack() {
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mUpdatePostServiceCallBack.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mUpdatePostServiceCallBack.noInternetFound();

            }

            @Override
            public void Successful() {
                mUpdatePostServiceCallBack.updatePostSuccessful();
            }

        });
    }

    @Override
    public void setPostContent(HashMap<String, Object> postContent) {
        this.postContent = postContent;
    }


    @Override
    public void setUpdatePostServiceCallBack(IUpdatePostServiceCallBack mUpdatePostServiceCallBack) {
        this.mUpdatePostServiceCallBack = mUpdatePostServiceCallBack;
    }
}
