package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;


public class GetNotificationsService implements IGetNotificationsService {


    private IGetNotificationsServiceCallback mGetNotificationsServiceCallback;
    private IGetDataByUseChildEventListenerService mGetDataByUseAddValueEventService;
    private CountDownTimer continueTimer;
    private boolean mIsTimeOut;


    public GetNotificationsService(IGetDataByUseChildEventListenerService getDataByUseAddValueEventService) {
        this.mGetDataByUseAddValueEventService = getDataByUseAddValueEventService;
    }


    private void timeOutFirebase() {
        new Handler(Looper.getMainLooper()).post(() -> continueTimer = new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {
                Log.d("", "");
            }

            public void onFinish() {
                if (!mIsTimeOut) {
                    mIsTimeOut = true;
                    mGetNotificationsServiceCallback.noNotifications();
                }
                continueTimer.cancel();
            }
        }.start());
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


                if (!StringEmptyUtil.isEmptyString(notificationsModel.getFullName())) {
                    mIsTimeOut = true;
                    continueTimer.cancel();
                    mGetNotificationsServiceCallback.onGetNotifications(notificationsModel);
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


                if (!StringEmptyUtil.isEmptyString(notificationsModel.getFullName())) {
                    mIsTimeOut = true;
                    continueTimer.cancel();
                    mGetNotificationsServiceCallback.onGetNotifications(notificationsModel);
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

        timeOutFirebase();
        mGetDataByUseAddValueEventService.getData();
    }


    @Override
    public void removeEventListener() {
        mGetDataByUseAddValueEventService.removeEventListener();

    }

    @Override
    public void setUpGetNotificationsServiceCallback(IGetNotificationsServiceCallback getNotificationsServiceCallback) {
        this.mGetNotificationsServiceCallback = getNotificationsServiceCallback;
    }


}
