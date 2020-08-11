package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public interface IFriendProfilePresenter {
    void getStateOfUser(String friendKey);

    void updateFollowers();

    void updateFollowing();

    void updateUnFollowing();

    void updateUnFollowers();

    void updateNotificationOfUser(NotificationsModel notificationsModel);

    void setUpFriendProfilePresenterCallBack(IFriendProfilePresenterCallBack mFriendProfilePresenterCallBack);
}
