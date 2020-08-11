package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters;

public interface IMutualFriendPresenter {

    void setUpMutualFriendPresenterCallBack(IMutualFriendPresenterCallBack mMutualFriendPresenterCallBack);

    void reSetIndex();

    void getFriendsOfCurrentUser();

    void getUser();
}
