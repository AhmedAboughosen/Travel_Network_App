package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserInfoModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

import java.util.List;

public interface IProfilePresenterCallBack {
    void userData(UserModel mUserModel);

    void ExceptionMessage(String message);

    void noUser(String message);

    void selectedInterest(List<String> list);

    void userCounterData(UserInfoModel userInfoModel);

    void internetIsNotConnected();

    void listOfFollowersOfUser(List<String> lis);
    void listOfFollowingsOfUser(List<String> lis);
}
