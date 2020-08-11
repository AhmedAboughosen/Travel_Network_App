package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NOTIFICATIONS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class AddNotificationsService implements IAddNotificationsService {


    private ISaveRawDataService mSaveRawDataService;
    private IAddNotificationsServiceCallback mAddNotificationsServiceCallback;

    public AddNotificationsService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    @Override
    public void setUpAddNotificationsServiceCallback(IAddNotificationsServiceCallback addNotificationsServiceCallback) {
        this.mAddNotificationsServiceCallback = addNotificationsServiceCallback;
    }


    @Override
    public void updateNotificationOfUser(NotificationsModel notificationsModel) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(NOTIFICATIONS).child(notificationsModel.getFriendKey()).child(notificationsModel.getUserKey());
        mSaveRawDataService.setDatabaseReference(databaseReference);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(UserNode.FULLNAME, notificationsModel.getFullName());
        hashMap.put(UserNode.PROFILE_IMAGE, notificationsModel.getProfileImage());
        hashMap.put(TIMESTAMP, ServerValue.TIMESTAMP);

        mSaveRawDataService.setMapData(hashMap);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                mAddNotificationsServiceCallback.updateNotificationSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mAddNotificationsServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddNotificationsServiceCallback.noInternetFound();
            }
        });

        mSaveRawDataService.updateData();
    }


}
