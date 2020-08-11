package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.BIO;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.COUNTRY_FLAG;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.COUNTRY_NAME;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.DATE_OF_BIRTH;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.FULLNAME;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.GENDER;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.JOINED_DATE;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.PROFILE_IMAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.USER;

/**
 * the responsibility of this Service is send request to firebase to ger user object by use key Value.
 */
public class GetUserInfoService implements IGetUserInfoService {


    private IGetDataByUseSingleValueService mGetDataService;
    private IGetUserInfoServiceCallBack mGetUserInfoServiceCallBack;

    public GetUserInfoService(IGetDataByUseSingleValueService getDataService) {
        this.mGetDataService = getDataService;
    }


    @Override
    public void getUserInfo(String userKey) {

        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child(USER).child(userKey);


        this.mGetDataService.setUpDatabaseReference(UsersRef);

        this.mGetDataService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    UserModel userModel = new UserModel();
                    if (dataSnapshot.exists()) {
                        userModel.getUserInfoModel().setKeyOfUser(dataSnapshot.getKey());

                        if (dataSnapshot.hasChild(FULLNAME)) {
                            String fullName = (Objects.requireNonNull(dataSnapshot.child(FULLNAME).getValue()).toString());
                            userModel.setFullName(fullName);
                        }

                        if (dataSnapshot.hasChild(BIO)) {
                            userModel.setBio(Objects.requireNonNull(dataSnapshot.child(BIO).getValue()).toString());
                        }


                        if (dataSnapshot.hasChild(PROFILE_IMAGE)) {
                            String profileImage = Objects.requireNonNull(dataSnapshot.child(PROFILE_IMAGE).getValue()).toString();
                            userModel.setProfilePicture(profileImage);
                        }

                        if (dataSnapshot.hasChild(COUNTRY_NAME)) {
                            userModel.setCountry(Objects.requireNonNull(dataSnapshot.child(COUNTRY_NAME).getValue()).toString());
                        }

                        if (dataSnapshot.hasChild(JOINED_DATE)) {
                            String joined_date = Objects.requireNonNull(Objects.requireNonNull(dataSnapshot.child(JOINED_DATE).getValue()).toString());
                            userModel.getUserInfoModel().setJoined_date(joined_date);
                        }


                        if (dataSnapshot.hasChild(COUNTRY_FLAG)) {
                            userModel.getUserInfoModel().setCountryFlag(Objects.requireNonNull(dataSnapshot.child(COUNTRY_FLAG).getValue()).toString());
                        }

                        if (dataSnapshot.hasChild(GENDER)) {
                            boolean gender = Boolean.parseBoolean(Objects.requireNonNull(dataSnapshot.child(GENDER).getValue()).toString());
                            userModel.setGender((gender));
                        }

                        if (dataSnapshot.hasChild(DATE_OF_BIRTH)) {
                            String date_of_birth = (Objects.requireNonNull(dataSnapshot.child(DATE_OF_BIRTH).getValue()).toString());
                            userModel.setDate_of_birth((date_of_birth));
                        }

                        mGetUserInfoServiceCallBack.userData(userModel);

                    } else {
                        mGetUserInfoServiceCallBack.thisUserNotExists();
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetUserInfoServiceCallBack.showMessageError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetUserInfoServiceCallBack.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetUserInfoServiceCallBack.noInternetFound();
            }
        });


        this.mGetDataService.getData();

    }


    @Override
    public void setUpGetUserInfoServiceCallBack(IGetUserInfoServiceCallBack mGetUserInfoServiceCallBack) {
        this.mGetUserInfoServiceCallBack = mGetUserInfoServiceCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetDataService.removeEventListener();
    }

}
