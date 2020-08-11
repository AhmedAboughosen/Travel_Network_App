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

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_RATING;

/**
 * the responsibility of this class update followers of friend and Increase Rating of friend
 */
public class UpdateFollowersService implements IUpdateFollowersService {


    private ISaveRawDataService mSaveRawDataService;
    private IUpdateFollowersServiceCallBack mUpdateFollowersServiceCallBack;

    public UpdateFollowersService(ISaveRawDataService saveRawDataService) {
        this.mSaveRawDataService = saveRawDataService;
    }

    @Override
    public void updateFollowers(String mFriendKey) {

        HashMap<String, Object> FollowersMap = new HashMap<>();
        FollowersMap.put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);
        DatabaseReference followersRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWERS).child(mFriendKey);
        mSaveRawDataService.setMapData(FollowersMap);
        mSaveRawDataService.setDatabaseReference(followersRefer);

        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                updateNumberOfFollowers(mFriendKey);
            }

            @Override
            public void showMessageError(String message) {
                mUpdateFollowersServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mUpdateFollowersServiceCallBack.noInternetFound();
            }
        });

        mSaveRawDataService.updateData();


    }

    /**
     * updateNumberOfFollowers
     *
     * @param mFriendKey
     */
    private void updateNumberOfFollowers(String mFriendKey) {
        //get number of Followers from user table then update it by one
        DatabaseReference userRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_COUNTER).child(mFriendKey).child(NUMBER_OF_FOLLOWERS);

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
                if (databaseError == null) {
                    IncreaseRating(mFriendKey);
                } else {
                    mUpdateFollowersServiceCallBack.showMessageError(databaseError.getMessage());
                }
            }
        });
    }


    /**
     * increase rating of user
     */
    private void IncreaseRating(String mFriendKey) {

        //get number of Followers from user table then update it by one
        DatabaseReference userRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_RATING).child(mFriendKey);

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
                if (databaseError == null) {
                    mUpdateFollowersServiceCallBack.updateFollowersSuccessful();
                } else {
                    mUpdateFollowersServiceCallBack.showMessageError(databaseError.getMessage());
                }

            }
        });

    }

    @Override
    public void setUpUpdateFollowersServiceCallBack(IUpdateFollowersServiceCallBack mUpdateFollowersServiceCallBack) {
        this.mUpdateFollowersServiceCallBack = mUpdateFollowersServiceCallBack;
    }
}
