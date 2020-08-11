package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices;

import androidx.annotation.NonNull;

public interface IIsCurrentUserFollowFriendServiceCallBack {
    void isOneOfFollowers(boolean isSate);

    void noInternetFound();

    void showMessageError(@NonNull String databaseError);
}
