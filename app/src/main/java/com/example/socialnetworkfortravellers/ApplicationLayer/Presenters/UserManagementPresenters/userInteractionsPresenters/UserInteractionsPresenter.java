package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userInteractionsPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IAddNotificationsService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices.IAddNotificationsServiceCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowersServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateFollowingServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowersService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowersServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowingService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices.IUpdateUnFollowingServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;

public class UserInteractionsPresenter implements IUserInteractionsPresenter {

    private IFollowPresenterCallBack mFollowPresenterCallBack;
    private IUpdateFollowersService mUpdateFollowersService;
    private IUpdateFollowingService mUpdateFollowingService;
    private IUpdateUnFollowingService mUpdateUnFollowingService;
    private IUpdateUnFollowersService mUpdateUnFollowersService;
    private String mFriendKey;
    private IAddNotificationsService mAddNotificationsService;

    public UserInteractionsPresenter(IUpdateFollowersService updateFollowersService,
                                     IUpdateFollowingService updateFollowingService, IUpdateUnFollowingService updateUnFollowingService,
                                     IUpdateUnFollowersService updateUnFollowersService,
                                     IAddNotificationsService addNotificationsService) {
        this.mUpdateFollowersService = updateFollowersService;
        this.mUpdateFollowingService = updateFollowingService;
        this.mUpdateUnFollowingService = updateUnFollowingService;
        this.mUpdateUnFollowersService = updateUnFollowersService;
        this.mAddNotificationsService = addNotificationsService;
    }

    @Override
    public void setFriendKey(String friendKey) {
        this.mFriendKey = friendKey;
    }

    @Override
    public void updateFollowers() {

        mUpdateFollowersService.setUpUpdateFollowersServiceCallBack(new IUpdateFollowersServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mFollowPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mFollowPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void updateFollowersSuccessful() {
                mFollowPresenterCallBack.updateFollowersSuccessful();
            }
        });
        mUpdateFollowersService.updateFollowers(mFriendKey);
    }


    @Override
    public void updateFollowing() {

        mUpdateFollowingService.setUpUpdateFollowingServiceCallBack(new IUpdateFollowingServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mFollowPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mFollowPresenterCallBack.networkErrorMessage();
            }

            @Override
            public void updateFollowingSuccessful() {
                mFollowPresenterCallBack.updateFollowingSuccessful();
            }
        });

        mUpdateFollowingService.updateFollowing(mFriendKey);
    }


    @Override
    public void updateUnFollowers() {

        mUpdateUnFollowersService.setUpUpdateUnFollowersServiceCallBack(new IUpdateUnFollowersServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mFollowPresenterCallBack.showMessageError(message);
            }

            @Override
            public void updateUnFollowersSuccessful() {
                mFollowPresenterCallBack.updateUnFollowersSuccessful();
            }
        });


        mUpdateUnFollowersService.updateUnFollowers(mFriendKey);

    }


    @Override
    public void updateNotificationOfUser(NotificationsModel notificationsModel) {
        mAddNotificationsService.setUpAddNotificationsServiceCallback(new IAddNotificationsServiceCallback() {
            @Override
            public void updateNotificationSuccessful() {
                mFollowPresenterCallBack.updateNotificationSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mFollowPresenterCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mFollowPresenterCallBack.networkErrorMessage();
            }
        });

        mAddNotificationsService.updateNotificationOfUser(notificationsModel);
    }

    @Override
    public void updateUnFollowing() {

        mUpdateUnFollowingService.setUpUpdateUnFollowingServiceCallBack(new IUpdateUnFollowingServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mFollowPresenterCallBack.showMessageError(message);
            }

            @Override
            public void updateUnFollowingSuccessful() {
                mFollowPresenterCallBack.updateUnFollowingSuccessful();
            }
        });

        mUpdateUnFollowingService.updateUnFollowing(mFriendKey);
    }

    public void setUpUserInteractionPresenterCallBack(IFollowPresenterCallBack mFollowPresenterCallBack) {
        this.mFollowPresenterCallBack = mFollowPresenterCallBack;
    }
}
