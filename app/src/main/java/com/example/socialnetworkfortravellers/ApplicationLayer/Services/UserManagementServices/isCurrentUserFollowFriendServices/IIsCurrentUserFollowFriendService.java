package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.isCurrentUserFollowFriendServices;

public interface IIsCurrentUserFollowFriendService {
    void getStateOfUser(String friendKey, String userId);
    void setUpIsCurrentUserFollowFriendServiceCallBack(IIsCurrentUserFollowFriendServiceCallBack mIsCurrentUserFollowFriendServiceCallBack);
}
