package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class IsCurrentUserFollowFriendService implements IIsCurrentUserFollowFriendService {

    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IIsCurrentUserFollowFriendServiceCallBack mIsCurrentUserFollowFriendServiceCallBack;

    public IsCurrentUserFollowFriendService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        this.mGetDataByUseSingleValueService = getDataByUseSingleValueService;
    }

    @Override
    public void getStateOfUser(String friendKey, String userId) {
        DatabaseReference followersRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWERS).child(friendKey).child(userId);

        mGetDataByUseSingleValueService.setUpDatabaseReference(followersRefer);
        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mIsCurrentUserFollowFriendServiceCallBack.isOneOfFollowers(dataSnapshot.exists());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mIsCurrentUserFollowFriendServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mIsCurrentUserFollowFriendServiceCallBack.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }

    @Override
    public void setUpIsCurrentUserFollowFriendServiceCallBack(IIsCurrentUserFollowFriendServiceCallBack mIsCurrentUserFollowFriendServiceCallBack) {
        this.mIsCurrentUserFollowFriendServiceCallBack = mIsCurrentUserFollowFriendServiceCallBack;
    }
}
