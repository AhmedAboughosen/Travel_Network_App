package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class UpdateUnFollowingService implements IUpdateUnFollowingService {

    private IUpdateUnFollowingServiceCallBack mUpdateUnFollowingServiceCallBack;

    @Override
    public void updateUnFollowing(String FriendKey) {

        DatabaseReference followingRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWING).child(CurrentUserIDUtil.getInstance().getCurrentUserID()).child(FriendKey);

        followingRefer.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                updateUserCounter();
            } else {
                String message = Objects.requireNonNull(task.getException()).getMessage();
                mUpdateUnFollowingServiceCallBack.showMessageError(message);
            }
        });
    }


    private void updateUserCounter() {
        //get number of Following from user table then decrease number  by one
        DatabaseReference userRefer = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_COUNTER).child(CurrentUserIDUtil.getInstance().getCurrentUserID()).child(NUMBER_OF_FOLLOWING);

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
                    mUpdateUnFollowingServiceCallBack.updateUnFollowingSuccessful();
                } else {
                    mUpdateUnFollowingServiceCallBack.showMessageError(databaseError.getMessage());
                }
            }
        });
    }


    @Override
    public void setUpUpdateUnFollowingServiceCallBack(IUpdateUnFollowingServiceCallBack mUpdateUnFollowingServiceCallBack) {
        this.mUpdateUnFollowingServiceCallBack = mUpdateUnFollowingServiceCallBack;
    }
}
