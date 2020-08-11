package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

import java.util.List;

public interface IFriendSuggestionPresenterCallBack {

    void thereIsNoDataToProvide();

    void listIsFinished();

    void addFakeData();


    void removeFakeData();


    void updateFriendsList(List<UserModel> users);


    void updateLoading(boolean isLoading);


    void showMessageError(MessageErrorModel message);

    void noInternetFound();

}
