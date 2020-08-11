package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class AddLikeToCommentService implements IAddLikeToCommentService {


    private IAddLikeToCommentServiceCallback mAddLikeToCommentServiceCallback;
    private ISaveRawDataService mSaveRawDataService;


    public AddLikeToCommentService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    /**
     * add like to comment key exists in obj
     *
     * @param databaseReference
     */
    public void addLikeToComment(DatabaseReference databaseReference) {

        HashMap<String, Object> map = new HashMap<>();
        map.put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);

        mSaveRawDataService.setDatabaseReference(databaseReference);
        mSaveRawDataService.setMapData(map);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddLikeToCommentServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddLikeToCommentServiceCallback.noInternetFound();
            }

            @Override
            public void Successful() {
                mAddLikeToCommentServiceCallback.addLikeToCommentSuccessful();
            }

        });


        mSaveRawDataService.updateData();
    }


    public void setUpAddLikeToCommentServiceCallback(IAddLikeToCommentServiceCallback mAddLikeToCommentServiceCallback) {
        this.mAddLikeToCommentServiceCallback = mAddLikeToCommentServiceCallback;
    }
}
