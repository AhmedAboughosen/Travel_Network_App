package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface IFriendProfilePresenterCallBack extends IPresenterCallBack {
    void isOneOfFollowers(boolean isSate);

    void updateFollowersSuccessful();

    void updateFollowingSuccessful();

    void updateUnFollowersSuccessful();

    void updateUnFollowingSuccessful();

    void updateNotificationSuccessful();

}
