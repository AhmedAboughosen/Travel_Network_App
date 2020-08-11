package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters;

import android.util.Log;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserInfoModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices.IGetAllFollowersOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowersOfUserServices.IGetAllFollowersOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices.IGetAllFollowingsOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getAllFollowingsOfUserServices.IGetAllFollowingsOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices.IGetInterestOfUserServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces.IGetUserCountService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces.IGetUserCountServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * responsibility of this class is to get user object from fire base.
 */
public class ProfilePresenter implements IProfilePresenter {


    private IGetUserCountService mGetUserCounterSerivce;
    private IGetUserInfoService mGetUserInfoService;
    private IProfilePresenterCallBack mProfilePresenterCallBack;
    private IGetInterestOfUserService mGetInterestOfUserService;
    private IGetAllFollowersOfUserService mGetAllFollowersOfUserService;
    private IGetAllFollowingsOfUserService mGetAllFollowingsOfUserService;


    public ProfilePresenter(IGetUserCountService getUserCounterSerivce, IGetUserInfoService getUserInfoService,
                            IGetInterestOfUserService getInterestOfUserService, IGetAllFollowersOfUserService getAllFollowersOfUserService, IGetAllFollowingsOfUserService getAllFollowingsOfUserService) {
        this.mGetUserCounterSerivce = getUserCounterSerivce;
        this.mGetUserInfoService = getUserInfoService;
        this.mGetInterestOfUserService = getInterestOfUserService;
        this.mGetAllFollowersOfUserService = getAllFollowersOfUserService;
        this.mGetAllFollowingsOfUserService = getAllFollowingsOfUserService;
    }


    @Override
    public void getUser(String userKey) {
        mGetUserInfoService.setUpGetUserInfoServiceCallBack(new IGetUserInfoServiceCallBack() {
            @Override
            public void userData(UserModel mUserModel) {
                mProfilePresenterCallBack.userData(mUserModel);
            }

            @Override
            public void showMessageError(String message) {
                Log.e("message", message);
                mProfilePresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void thisUserNotExists() {
                mProfilePresenterCallBack.noUser(StringConfigUtil.MESSAGE_PROBLEM);
            }

            @Override
            public void noInternetFound() {
                mProfilePresenterCallBack.internetIsNotConnected();
            }
        });
        mGetUserInfoService.getUserInfo(userKey);

    }


    @Override
    public void getUserCounter(String userKey) {
        mGetUserCounterSerivce.setUpGetUserCounterSerivceCallBack(new IGetUserCountServiceCallBack() {
            @Override
            public void userCountData(UserInfoModel mUserModel) {
                mProfilePresenterCallBack.userCounterData(mUserModel);
            }

            @Override
            public void ExceptionMessage(String message) {
                mProfilePresenterCallBack.ExceptionMessage(message);
            }


        });
        mGetUserCounterSerivce.getUserCount(userKey);

    }


    @Override
    public void getUserInterest(String userKey) {
        mGetInterestOfUserService.setUpGetInterestOfUserServiceCallBack(new IGetInterestOfUserServiceCallBack() {
            @Override
            public void selectedInterest(HashMap<String, Object> list) {

                //Getting Set of keys from HashMap
                Set<String> keySet = list.keySet();
                mProfilePresenterCallBack.selectedInterest(new ArrayList<>(keySet));
            }

            @Override
            public void showMessageError(String message) {
                mProfilePresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void noInternetFound() {
                mProfilePresenterCallBack.internetIsNotConnected();
            }
        });

        mGetInterestOfUserService.getInterestOfUser(userKey);

    }


    @Override
    public void getAllFollowersOfUser(String userKey) {
        mGetAllFollowersOfUserService.setUpGetAllFollowersOfUserServiceCallBack(new IGetAllFollowersOfUserServiceCallBack() {
            @Override
            public void listOfUser(List<String> lis) {
                mProfilePresenterCallBack.listOfFollowersOfUser(lis);
            }

            @Override
            public void showMessageError(String message) {
                mProfilePresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void noInternetFound() {
                mProfilePresenterCallBack.internetIsNotConnected();
            }
        });

        mGetAllFollowersOfUserService.getAllFollowerUsers(userKey);
    }


    @Override
    public void getAllFollowingsOfUser(String userKey) {
        mGetAllFollowingsOfUserService.setUpGetAllFollowingsOfUserServiceCallBack(new IGetAllFollowingsOfUserServiceCallBack() {
            @Override
            public void listOfUser(List<String> lis) {
                mProfilePresenterCallBack.listOfFollowingsOfUser(lis);
            }

            @Override
            public void showMessageError(String message) {
                mProfilePresenterCallBack.ExceptionMessage(message);
            }

            @Override
            public void noInternetFound() {
                mProfilePresenterCallBack.internetIsNotConnected();
            }
        });

        mGetAllFollowingsOfUserService.getAllFollowingUsers(userKey);
    }


    @Override
    public void setUpProfilePresenterCallBack(IProfilePresenterCallBack mUserProfilePresenterCallBack) {
        this.mProfilePresenterCallBack = mUserProfilePresenterCallBack;
    }

    @Override
    public void removeEventListener() {
        mGetInterestOfUserService.removeEventListener();
        mGetUserCounterSerivce.removeEventListener();
        mGetUserInfoService.removeEventListener();
        // mGetAllFollowingsOfUserService.removeEventListener();
        // mGetAllFollowersOfUserService.removeEventListener();
    }
}
