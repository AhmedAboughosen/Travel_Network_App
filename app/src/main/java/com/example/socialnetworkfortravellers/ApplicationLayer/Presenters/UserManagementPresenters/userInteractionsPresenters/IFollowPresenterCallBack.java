package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface IFollowPresenterCallBack extends IPresenterCallBack {


    void updateFollowersSuccessful();

    void updateFollowingSuccessful();

    void updateUnFollowersSuccessful();

    void updateUnFollowingSuccessful();

    void updateNotificationSuccessful();
}
