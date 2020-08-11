package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters;

public interface IProfilePresenter {
    void setUpProfilePresenterCallBack(IProfilePresenterCallBack mUserProfilePresenterCallBack);

    void getUserCounter(String userKey);

    void getUser(String userKey);

    void getUserInterest(String userKey);

    void getAllFollowersOfUser(String userKey);

    void getAllFollowingsOfUser(String userKey);

    void removeEventListener();
}
