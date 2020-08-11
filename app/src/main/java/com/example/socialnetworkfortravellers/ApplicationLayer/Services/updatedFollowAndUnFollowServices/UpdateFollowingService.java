package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class UpdateFollowingService implements IUpdateFollowingService {

    private ISaveRawDataService mSaveRawDataService;
    private IUpdateFollowingServiceCallBack mUpdateFollowingServiceCallBack;

    public UpdateFollowingService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }


    @Override
    public void updateFollowing(String mFriendKey) {

        HashMap<String, Object> FollowersMap = new HashMap<>();
        FollowersMap.put(mFriendKey, true);
        DatabaseReference followersRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWING).child(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mSaveRawDataService.setMapData(FollowersMap);
        mSaveRawDataService.setDatabaseReference(followersRefer);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                updateNumberOfFollowing();
            }

            @Override
            public void showMessageError(String message) {
                mUpdateFollowingServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mUpdateFollowingServiceCallBack.noInternetFound();
            }
        });

        mSaveRawDataService.updateData();
    }


    private void updateNumberOfFollowing() {
        //get number of Followers from user table then update it by one
        DatabaseReference userRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_COUNTER).child(CurrentUserIDUtil.getInstance().getCurrentUserID()).child(NUMBER_OF_FOLLOWING);

        userRefer.runTransaction(new Transaction.Handler() {
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NotNull MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    mutableData.setValue((Long) mutableData.getValue() + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                if (databaseError == null){
                    mUpdateFollowingServiceCallBack.updateFollowingSuccessful();
                }else {
                    mUpdateFollowingServiceCallBack.showMessageError(databaseError.getMessage());
                }

            }
        });

    }


    @Override
    public void setUpUpdateFollowingServiceCallBack(IUpdateFollowingServiceCallBack mUpdateFollowingServiceCallBack) {
        this.mUpdateFollowingServiceCallBack = mUpdateFollowingServiceCallBack;
    }
}
