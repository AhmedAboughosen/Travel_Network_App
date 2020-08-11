package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getFullNameOfUsersServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.FULLNAME;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.USER_NAME;

/**
 * responsibility of this class is to get fullNames fo all users
 */
public class GetFullNameOfUsersService implements IGetFullNameOfUsersService {


    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;
    private IGetFullNameOfUsersServiceCallBack mGetFullNameOfUsersServiceCallBack;

    public GetFullNameOfUsersService(IGetDataByUseSingleValueService singleValueService) {
        this.mGetDataByUseSingleValueService = singleValueService;
    }


    @Override
    public void getFullNameOfUsers() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(USER_NAME);
        mGetDataByUseSingleValueService.setUpDatabaseReference(databaseReference);

        ArrayList<FoundUserModel> allUsers = new ArrayList<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot userKey : dataSnapshot.getChildren()) {
                            FoundUserModel userModel = new FoundUserModel();

                            if (userKey.hasChild(FULLNAME)) {
                                userModel.setUserKey(userKey.getKey());
                                String fullName = (Objects.requireNonNull(userKey.child(FULLNAME).getValue()).toString());
                                userModel.setFullName(fullName);
                            }
                            allUsers.add(userModel);
                        }

                        mGetFullNameOfUsersServiceCallBack.listOfUser(allUsers);
                    } else {
                        mGetFullNameOfUsersServiceCallBack.noUserExists();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetFullNameOfUsersServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetFullNameOfUsersServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetFullNameOfUsersServiceCallBack.noInternetFound();

            }
        });
        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setUpGetFullNameOfUsersServiceCallBack(IGetFullNameOfUsersServiceCallBack mGetFullNameOfUsersServiceCallBack) {
        this.mGetFullNameOfUsersServiceCallBack = mGetFullNameOfUsersServiceCallBack;
    }
}
