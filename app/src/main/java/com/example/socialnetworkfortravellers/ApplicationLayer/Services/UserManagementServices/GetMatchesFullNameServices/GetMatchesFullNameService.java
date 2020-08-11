package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.GetMatchesFullNameServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseAddValueEventServiceCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class GetMatchesFullNameService implements IGetMatchesFullNameService {

    private IGetDataByUseAddValueEventService mGetDataService;
    private IGetMatchesFullNameServiceCallBack mFindFriendServiceCallBack;

    public GetMatchesFullNameService(IGetDataByUseAddValueEventService getDataService) {
        mGetDataService = getDataService;
    }


    @Override
    public void getAllFullNameOfUsers() {
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child("UserName");
        this.mGetDataService.setUpDatabaseReference(UsersRef);
        ArrayList<FoundUserModel> allUsers = new ArrayList<>();

        this.mGetDataService.setUpGetDataServiceCallBack(new IGetDataByUseAddValueEventServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                            FoundUserModel userModel = new FoundUserModel();

                            if (userKey.hasChild("fullname")) {
                                userModel.setUserKey(userKey.getKey());
                                String fullName = (Objects.requireNonNull(userKey.child("fullname").getValue()).toString());
                                userModel.setFullName(fullName);
                            }
                            allUsers.add(userModel);
                        }

                        mFindFriendServiceCallBack.allUsers(allUsers);
                    } else {
                        mFindFriendServiceCallBack.noUserExists();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mFindFriendServiceCallBack.errorMessage(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mFindFriendServiceCallBack.errorMessage(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {

            }
        });

        this.mGetDataService.getData();
    }

    @Override
    public void setFindFriendServiceCallBack(IGetMatchesFullNameServiceCallBack findFriendServiceCallBack) {
        mFindFriendServiceCallBack = findFriendServiceCallBack;
    }
}
