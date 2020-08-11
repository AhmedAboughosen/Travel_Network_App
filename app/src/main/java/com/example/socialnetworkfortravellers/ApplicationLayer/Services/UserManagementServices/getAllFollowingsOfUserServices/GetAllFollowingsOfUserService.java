package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices;

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

import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

/**
 * responsibility of this class is to get followings of passed user.
 */
public class GetAllFollowingsOfUserService implements IGetAllFollowingsOfUserService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetAllFollowingsOfUserServiceCallBack mGetAllFollowingsOfUserServiceCallBack;

    public GetAllFollowingsOfUserService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    @Override
    public void getAllFollowingUsers(String userKey) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(FOLLOWING).child(userKey);
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
                    mGetAllFollowingsOfUserServiceCallBack.listOfUser(users);
                } else {
                    if (userKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                        mGetAllFollowingsOfUserServiceCallBack.showMessageError("you don't have  any followings");
                    } else {
                        mGetAllFollowingsOfUserServiceCallBack.showMessageError("this user does not have any followings");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetAllFollowingsOfUserServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetAllFollowingsOfUserServiceCallBack.noInternetFound();

            }
        });
        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setUpGetAllFollowingsOfUserServiceCallBack(IGetAllFollowingsOfUserServiceCallBack mGetAllFollowingsOfUserServiceCallBack) {
        this.mGetAllFollowingsOfUserServiceCallBack = mGetAllFollowingsOfUserServiceCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetDataByUseSingleValueService.removeEventListener();
    }
}
