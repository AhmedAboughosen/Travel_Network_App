package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserInfoModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.NUMBER_OF_POSTS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWERS;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.NUMBER_OF_FOLLOWING;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_COUNTER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserInteractionNode.USER_INTERACTIONS;

public class GetUserCountService implements IGetUserCountService {


    private IGetDataByUseAddValueEventService mGetDataService;
    private IGetUserCountServiceCallBack mGetUserCounterSerivceCallBack;

    public GetUserCountService(IGetDataByUseAddValueEventService getDataService) {
        this.mGetDataService = getDataService;
    }


    /**
     * get user count from DB such as number of followers and following and posts.
     */
    @Override
    public void getUserCount(String userKey) {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child(USER_INTERACTIONS).child(USER_COUNTER).child(userKey);


        this.mGetDataService.setUpDatabaseReference(UsersRef);

        mGetDataService.setUpGetDataServiceCallBack(new IGetDataByUseAddValueEventServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    UserInfoModel userInfoModelModel = new UserInfoModel();
                    if (dataSnapshot.exists()) {

                        if (dataSnapshot.hasChild(NUMBER_OF_FOLLOWERS)) {
                            userInfoModelModel.setNumber_Of_Followers(Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(NUMBER_OF_FOLLOWERS).getValue()).toString()));
                        }

                        if (dataSnapshot.hasChild(NUMBER_OF_FOLLOWING)) {
                            userInfoModelModel.setNumber_Of_Following(Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(NUMBER_OF_FOLLOWING).getValue()).toString()));
                        }

                        if (dataSnapshot.hasChild(NUMBER_OF_POSTS)) {
                            userInfoModelModel.setNumber_Of_Posts(Integer.parseInt(Objects.requireNonNull(dataSnapshot.child(NUMBER_OF_POSTS).getValue()).toString()));
                        }

                        mGetUserCounterSerivceCallBack.userCountData(userInfoModelModel);
                    } else {
                        mGetUserCounterSerivceCallBack.userCountData(null);
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetUserCounterSerivceCallBack.ExceptionMessage(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetUserCounterSerivceCallBack.ExceptionMessage(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetUserCounterSerivceCallBack.ExceptionMessage(StringConfigUtil.NO_INTEREST_USER_EXISTS);
            }
        });

        mGetDataService.getData();
    }


    @Override
    public void setUpGetUserCounterSerivceCallBack(IGetUserCountServiceCallBack mGetUserCounterSerivceCallBack) {
        this.mGetUserCounterSerivceCallBack = mGetUserCounterSerivceCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetDataService.removeEventListener();
    }

}
