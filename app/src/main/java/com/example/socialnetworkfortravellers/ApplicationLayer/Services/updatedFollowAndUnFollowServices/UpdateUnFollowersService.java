package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_RATING;

public class UpdateUnFollowersService implements IUpdateUnFollowersService {


    private DatabaseReference mDatabaseReference;
    private IUpdateUnFollowersServiceCallBack mUpdateUnFollowersServiceCallBack;

    public UpdateUnFollowersService() {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
    }


    private void updateUserCount(String FriendKey) {

        //get number of Followers from user table then decrease number  by one
        DatabaseReference userRefer = mDatabaseReference.child(USER_INTERACTIONS).child(USER_COUNTER).child(FriendKey).child(NUMBER_OF_FOLLOWERS);

        userRefer.runTransaction(new Transaction.Handler() {
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NotNull MutableData mutableData) {
                if (mutableData.getValue() != null) {
                    mutableData.setValue((Long) mutableData.getValue() - 1);
                }

                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                if (databaseError == null) {
                    decreaseRating(FriendKey);
                } else {
                    mUpdateUnFollowersServiceCallBack.showMessageError(databaseError.getMessage());
                }
            }
        });
    }


    @Override
    public void updateUnFollowers(String FriendKey) {

        DatabaseReference followersRefer = mDatabaseReference.child(USER_INTERACTIONS).child(FOLLOWERS).child(FriendKey).child(CurrentUserIDUtil.getInstance().getCurrentUserID());

        followersRefer.removeValue().addOnCompleteListener(task -> {

            if (task.isSuccessful()) {
                updateUserCount(FriendKey);
            } else {
                mUpdateUnFollowersServiceCallBack.showMessageError(task.getException().getMessage());
            }

        });

    }


    /**
     * increase rating of user
     *
     * @param FriendKey
     */
    private void decreaseRating(String FriendKey) {
        DatabaseReference RatingRef = FirebaseDatabase.getInstance().getReference();

        //get number of Followers from user table then decrease number  by one
        DatabaseReference userRefer = RatingRef.child(USER_INTERACTIONS).child(USER_RATING).child(FriendKey);

        userRefer.runTransaction(new Transaction.Handler() {
            @NotNull
            @Override
            public Transaction.Result doTransaction(@NotNull MutableData mutableData) {

                if (mutableData.getValue() != null) {
                    mutableData.setValue((Long) mutableData.getValue() - 1);
                }


                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                if (databaseError == null) {
                    mUpdateUnFollowersServiceCallBack.updateUnFollowersSuccessful();
                } else {
                    mUpdateUnFollowersServiceCallBack.showMessageError(databaseError.getMessage());
                }
            }
        });

    }

    @Override
    public void setUpUpdateUnFollowersServiceCallBack(IUpdateUnFollowersServiceCallBack mUpdateUnFollowersServiceCallBack) {
        this.mUpdateUnFollowersServiceCallBack = mUpdateUnFollowersServiceCallBack;
    }
}
