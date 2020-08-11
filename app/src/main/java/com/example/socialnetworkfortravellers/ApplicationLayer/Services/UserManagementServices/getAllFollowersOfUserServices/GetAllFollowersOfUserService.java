package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

/**
 * responsibility of this class is to get followers of passed user.
 */
public class GetAllFollowersOfUserService implements IGetAllFollowersOfUserService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetAllFollowersOfUserServiceCallBack mGetAllFollowersOfUserServiceCallBack;

    public GetAllFollowersOfUserService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    @Override
    public void getAllFollowerUsers(String userKey) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWERS).child(userKey);
        mGetDataByUseSingleValueService.setUpDatabaseReference(databaseReference);

        List<String> users = new ArrayList<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                        String key = userKey.getKey();
                        users.add(key);
                    }
                    mGetAllFollowersOfUserServiceCallBack.listOfUser(users);
                } else {
                    if (userKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                        mGetAllFollowersOfUserServiceCallBack.showMessageError("you don't have  any followers");
                    } else {
                        mGetAllFollowersOfUserServiceCallBack.showMessageError("this user does not have any followers");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetAllFollowersOfUserServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetAllFollowersOfUserServiceCallBack.noInternetFound();

            }
        });
        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setUpGetAllFollowersOfUserServiceCallBack(IGetAllFollowersOfUserServiceCallBack mGetAllFollowersOfUserServiceCallBack) {
        this.mGetAllFollowersOfUserServiceCallBack = mGetAllFollowersOfUserServiceCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetDataByUseSingleValueService.removeEventListener();
    }
}
