package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addRepliesServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.IS_HAS_REPLIES;

public class AddRepliesService implements IAddRepliesService {


    private ISaveRawDataService mSaveRawDataService;
    private IAddRepliesServiceCallBack mAddRepliesServiceCallBack;

    public AddRepliesService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    /**
     * update Replies State from enabled to disabled
     *
     * @param databaseReference
     * @param is_has_replies
     */
    @Override
    public void updateRepliesState(DatabaseReference databaseReference, boolean is_has_replies) {

        mSaveRawDataService.setDatabaseReference(databaseReference);

        HashMap<String, Object> map = new HashMap<>();
        map.put(IS_HAS_REPLIES, is_has_replies);
        mSaveRawDataService.setMapData(map);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mAddRepliesServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddRepliesServiceCallBack.noInternetFound();
            }

            @Override
            public void Successful() {
                mAddRepliesServiceCallBack.addRepliesSuccessful();
            }
        });

        mSaveRawDataService.updateData();
    }

    @Override
    public void setUpAddRepliesServiceCallBack(IAddRepliesServiceCallBack mAddRepliesServiceCallBack) {
        this.mAddRepliesServiceCallBack = mAddRepliesServiceCallBack;
    }
}
