package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getFriendsOfUserServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * responsibility of this class is get friends which user follow.
 */
public class GetFriendsOfUserService implements IGetFriendsOfUserService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetFriendsOfUserServiceCallBack mGetFollowingDataServiceCallBack;

    public GetFriendsOfUserService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        this.mGetDataByUseSingleValueService = getDataByUseSingleValueService;
    }

    @Override
    public void setUpGetFollowingDataServiceCallBack(IGetFriendsOfUserServiceCallBack mGetFollowingDataServiceCallBack) {
        this.mGetFollowingDataServiceCallBack = mGetFollowingDataServiceCallBack;
    }


    /**
     * pass user id send request to server to get data under following path.
     *
     * @param userId
     */
    @Override
    public void getFriendsOfUser(String userId) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child(UserInteractionNode.USER_INTERACTIONS).child(UserInteractionNode.FOLLOWING).child(userId);
        mGetDataByUseSingleValueService.setUpDatabaseReference(UsersRef);
        HashMap<String, String> users = new HashMap<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                            String key = userKey.getKey();
                            users.put(key, userId);
                        }
                        mGetFollowingDataServiceCallBack.getFriends(users);
                    } else {
                        mGetFollowingDataServiceCallBack.CurrentUserDoesNotHaveFriends();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetFollowingDataServiceCallBack.showMessageError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                mGetFollowingDataServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetFollowingDataServiceCallBack.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }
}
