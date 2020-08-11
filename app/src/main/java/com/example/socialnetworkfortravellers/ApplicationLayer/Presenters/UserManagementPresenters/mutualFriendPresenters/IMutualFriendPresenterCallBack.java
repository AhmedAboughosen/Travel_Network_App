package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

import java.util.List;

public interface IMutualFriendPresenterCallBack extends IBaseServiceCallBack {

    void currentUserDoesNotHaveFriends();


    void addFakeData();


    void removeFakeData();


    void updateFriendsList(List<UserModel> users);


    void updateLoading(boolean isLoading);

    void listIsFinished();

}
