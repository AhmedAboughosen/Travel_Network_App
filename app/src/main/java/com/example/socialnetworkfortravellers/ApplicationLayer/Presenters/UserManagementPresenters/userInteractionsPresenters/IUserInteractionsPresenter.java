package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public interface IUserInteractionsPresenter {
    void updateFollowers();

    void updateFollowing();

    void updateUnFollowing();

    void updateUnFollowers();

    void setFriendKey(String friendKey);

    void updateNotificationOfUser(NotificationsModel notificationsModel);


    void setUpUserInteractionPresenterCallBack(IFollowPresenterCallBack mFollowPresenterCallBack);
}
