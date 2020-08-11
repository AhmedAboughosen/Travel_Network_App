package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.eventBus.ListOfNotificationsEvent;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.greenrobot.eventbus.EventBus;

import java.util.Objects;


public class GetNewNotificationsService implements IGetNewNotificationsService {


    private IBaseServiceCallBack mGetNotificationsServiceCallback;
    private IGetDataByUseChildEventListenerService mGetDataByUseAddValueEventService;


    public GetNewNotificationsService(IGetDataByUseChildEventListenerService getDataByUseAddValueEventService) {
        this.mGetDataByUseAddValueEventService = getDataByUseAddValueEventService;
    }


    /**
     * @param userKey
     */
    @Override
    public void getNotifications(String userKey) {

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(UserInteractionNode.USER_INTERACTIONS).child(UserInteractionNode.NOTIFICATIONS).child(userKey);

        mGetDataByUseAddValueEventService.setUpDatabaseReference(databaseReference);


        mGetDataByUseAddValueEventService.setUpGetDataByUseChildEventListenerServiceCallBack(new IGetDataByUseChildEventListenerServiceCallBack() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                NotificationsModel notificationsModel = new NotificationsModel();
                notificationsModel.setFriendKey(key);

                if (dataSnapshot.hasChild(UserNode.FULLNAME)) {
                    notificationsModel.setFullName(Objects.requireNonNull(dataSnapshot.child(UserNode.FULLNAME).getValue()).toString());
                }

                if (dataSnapshot.hasChild(UserNode.PROFILE_IMAGE)) {
                    notificationsModel.setProfileImage(Objects.requireNonNull(dataSnapshot.child(UserNode.PROFILE_IMAGE).getValue()).toString());
                }

                if (dataSnapshot.hasChild(PostNode.TIMESTAMP)) {
                    notificationsModel.setDate(Objects.requireNonNull(dataSnapshot.child(PostNode.TIMESTAMP).getValue()));
                }

                if (ConvertTimeUtil.toMinutes(notificationsModel.getDate().toString()) <= 10) {
                    if (!StringEmptyUtil.isEmptyString(notificationsModel.getFullName())) {
                        EventBus.getDefault().postSticky(new ListOfNotificationsEvent(notificationsModel));
                    }
                }


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String key = dataSnapshot.getKey();
                NotificationsModel notificationsModel = new NotificationsModel();
                notificationsModel.setFriendKey(key);

                if (dataSnapshot.hasChild(UserNode.FULLNAME)) {
                    notificationsModel.setFullName(Objects.requireNonNull(dataSnapshot.child(UserNode.FULLNAME).getValue()).toString());
                }

                if (dataSnapshot.hasChild(UserNode.PROFILE_IMAGE)) {
                    notificationsModel.setProfileImage(Objects.requireNonNull(dataSnapshot.child(UserNode.PROFILE_IMAGE).getValue()).toString());
                }

                if (dataSnapshot.hasChild(PostNode.TIMESTAMP)) {
                    notificationsModel.setDate(Objects.requireNonNull(dataSnapshot.child(PostNode.TIMESTAMP).getValue()));
                }


                if (ConvertTimeUtil.toMinutes(notificationsModel.getDate().toString()) <= 10) {
                    if (!StringEmptyUtil.isEmptyString(notificationsModel.getFullName())) {
                        EventBus.getDefault().postSticky(new ListOfNotificationsEvent(notificationsModel));
                    }
                }

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetNotificationsServiceCallback.showMessageError(databaseError.getMessage());
            }

            @Override
            public void showMessageError(String message) {
                mGetNotificationsServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mGetNotificationsServiceCallback.noInternetFound();
            }
        });

        mGetDataByUseAddValueEventService.getData();
    }


    @Override
    public void removeEventListener() {
        mGetDataByUseAddValueEventService.removeEventListener();

    }

    @Override
    public void setUpBaseServiceCallBack(IBaseServiceCallBack baseServiceCallBack) {
        this.mGetNotificationsServiceCallback = baseServiceCallBack;
    }
}
