package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters.IFollowPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters.IUserInteractionsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices.IIsCurrentUserFollowFriendService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices.IIsCurrentUserFollowFriendServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

public class FriendProfilePresenter implements IFriendProfilePresenter {


    private IFriendProfilePresenterCallBack mFriendProfilePresenterCallBack;
    private IIsCurrentUserFollowFriendService mIsCurrentUserFollowFriendService;
    private IUserInteractionsPresenter mFollowPresenter;

    public FriendProfilePresenter(IUserInteractionsPresenter followPresenter, IIsCurrentUserFollowFriendService currentUserFollowFriendService
    ) {
        this.mIsCurrentUserFollowFriendService = currentUserFollowFriendService;
        this.mFollowPresenter = followPresenter;
        setUpUserInteractionsPresenterCallBack();
        setUpIsCurrentUserFollowFriendServiceCallBack();
    }

    private void setUpUserInteractionsPresenterCallBack() {
        mFollowPresenter.setUpUserInteractionPresenterCallBack(new IFollowPresenterCallBack() {
            @Override
            public void showMessageError(@NonNull String databaseError) {
                mFriendProfilePresenterCallBack.showMessageError(databaseError);
            }

            @Override
            public void networkErrorMessage() {
                mFriendProfilePresenterCallBack.networkErrorMessage();
            }

            @Override
            public void updateFollowersSuccessful() {
                mFriendProfilePresenterCallBack.updateFollowersSuccessful();
            }

            @Override
            public void updateFollowingSuccessful() {
                mFriendProfilePresenterCallBack.updateFollowingSuccessful();
            }

            @Override
            public void updateUnFollowersSuccessful() {
                mFriendProfilePresenterCallBack.updateUnFollowersSuccessful();
            }

            @Override
            public void updateUnFollowingSuccessful() {
                mFriendProfilePresenterCallBack.updateUnFollowingSuccessful();
            }

            @Override
            public void updateNotificationSuccessful() {
                mFriendProfilePresenterCallBack.updateNotificationSuccessful();
            }
        });
    }

    @Override
    public void getStateOfUser(String friendKey) {
        this.mFollowPresenter.setFriendKey(friendKey);
        mIsCurrentUserFollowFriendService.getStateOfUser(friendKey, CurrentUserIDUtil.getInstance().getCurrentUserID());
    }

    private void setUpIsCurrentUserFollowFriendServiceCallBack() {
        mIsCurrentUserFollowFriendService.setUpIsCurrentUserFollowFriendServiceCallBack(new IIsCurrentUserFollowFriendServiceCallBack() {
            @Override
            public void isOneOfFollowers(boolean isSate) {
                mFriendProfilePresenterCallBack.isOneOfFollowers(isSate);
            }

            @Override
            public void noInternetFound() {
                mFriendProfilePresenterCallBack.networkErrorMessage();

            }

            @Override
            public void showMessageError(@NonNull String databaseError) {
                mFriendProfilePresenterCallBack.showMessageError(databaseError);

            }
        });
    }

    @Override
    public void setUpFriendProfilePresenterCallBack(IFriendProfilePresenterCallBack mFriendProfilePresenterCallBack) {
        this.mFriendProfilePresenterCallBack = mFriendProfilePresenterCallBack;
    }


    public void updateNotificationOfUser(NotificationsModel notificationsModel) {
        this.mFollowPresenter.updateNotificationOfUser(notificationsModel);
    }


    @Override
    public void updateFollowers() {
        this.mFollowPresenter.updateFollowers();
    }

    @Override
    public void updateFollowing() {
        this.mFollowPresenter.updateFollowing();
    }


    @Override
    public void updateUnFollowers() {
        this.mFollowPresenter.updateUnFollowers();
    }


    @Override
    public void updateUnFollowing() {
        this.mFollowPresenter.updateUnFollowing();
    }
}
