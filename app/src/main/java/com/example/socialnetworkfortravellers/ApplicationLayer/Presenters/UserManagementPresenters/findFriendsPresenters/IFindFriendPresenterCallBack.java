package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.List;

public interface IFindFriendPresenterCallBack extends IPresenterCallBack {


    void noFriendExists();

    void addFakeData();


    void removeFakeData();


    void updateFriendsList(List<UserModel> users);


    void updateLoading(boolean isLoading);

    void listIsFinished();
}
