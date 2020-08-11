package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveUserInfoServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues;
import com.example.socialnetworkfortravellers.nodesLayer.UserNode;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.COUNTRY;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.FULLNAME;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.USER_NAME;

public class SaveUserInfoService implements ISaveUserInfoService {

    private ISaveUserInfoServiceCallback mSetUpUserInfoServiceCallback;
    private ISaveRawDataService mSaveInfoService;
    private UserModel mUserModel;


    public SaveUserInfoService(ISaveRawDataService mSaveRawDataService) {
        this.mSaveInfoService = mSaveRawDataService;
    }


    /**
     * save user  data  in Node user
     */
    @Override
    public void saveUserInfo(UserModel userModel) {
        this.mUserModel = userModel;

        String CurrentUserID = CurrentUserIDUtil.getInstance().getCurrentUserID();
        DatabaseReference UsersRef = FirebaseDatabase.getInstance().getReference().child(UserNode.USER).child(CurrentUserID);
        mSaveInfoService.setDatabaseReference(UsersRef);
        HashMap<String, Object> userMap = new HashMap<>();
        userMap.put(UserNode.FULLNAME, mUserModel.getFullName());
        userMap.put(UserNode.GENDER, mUserModel.getGender());
        userMap.put(UserNode.DATE_OF_BIRTH, mUserModel.getDate_of_birth());
        userMap.put(UserNode.COUNTRY_NAME, mUserModel.getCountry());
        userMap.put(UserNode.COUNTRY_FLAG, mUserModel.getUserInfoModel().getCountryFlag());
        userMap.put(UserNode.JOINED_DATE, mUserModel.getUserInfoModel().getJoined_date());
        userMap.put(UserNode.BIO, mUserModel.getBio());


        mSaveInfoService.setMapData(userMap);
        setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mSetUpUserInfoServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mSetUpUserInfoServiceCallback.noInternetFound();
            }

            @Override
            public void Successful() {
                saveCountryOfUser();
            }

        });
        mSaveInfoService.updateData();
    }


    /**
     * save country in node of user to get country of user faster
     */
    private void saveCountryOfUser() {
        DatabaseReference mCountryRef = FirebaseDatabase.getInstance().getReference().child(COUNTRY).child(mUserModel.getCountry());
        HashMap<String, Object> countryMap = new HashMap<>();
        countryMap.put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);
        mSaveInfoService.setDatabaseReference(mCountryRef);
        mSaveInfoService.setMapData(countryMap);
        setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                saveFullNameOfUser();
            }

            @Override
            public void showMessageError(String message) {
                mSetUpUserInfoServiceCallback.showMessageError(ConstantValues.SERVER_PROBLEM);
            }

            @Override
            public void noInternetFound() {
                mSetUpUserInfoServiceCallback.noInternetFound();
            }

        });
        mSaveInfoService.updateData();
    }


    /**
     * this method used to save full name of user to user in search algorithm for faster.
     */
    private void saveFullNameOfUser() {
        DatabaseReference mFullUsersRef = FirebaseDatabase.getInstance().getReference().child(USER_NAME).child(CurrentUserIDUtil.getInstance().getCurrentUserID());
        HashMap<String, Object> countryMap = new HashMap<>();
        countryMap.put(FULLNAME, mUserModel.getFullName());
        mSaveInfoService.setDatabaseReference(mFullUsersRef);
        mSaveInfoService.setMapData(countryMap);
        setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                mSetUpUserInfoServiceCallback.saveUserSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mSetUpUserInfoServiceCallback.showMessageError(ConstantValues.SERVER_PROBLEM);
            }

            @Override
            public void noInternetFound() {
                mSetUpUserInfoServiceCallback.noInternetFound();
            }

        });
        mSaveInfoService.updateData();

    }

    private void setUpSaveInfoServiceCallBack(ISaveRawDataServiceCallBack saveInfoServiceCallBack) {
        mSaveInfoService.setUpSaveInfoServiceCallBack(saveInfoServiceCallBack);
    }


    @Override
    public void setUpSetUpUserInfoServiceCallback(ISaveUserInfoServiceCallback mSetUpUserInfoServiceCallback) {
        this.mSetUpUserInfoServiceCallback = mSetUpUserInfoServiceCallback;
    }
}
