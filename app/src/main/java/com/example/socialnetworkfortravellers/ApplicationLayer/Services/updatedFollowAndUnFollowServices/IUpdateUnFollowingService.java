package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

public interface IUpdateUnFollowingService {
    void updateUnFollowing(String FriendKey);

    void setUpUpdateUnFollowingServiceCallBack(IUpdateUnFollowingServiceCallBack mUpdateUnFollowingServiceCallBack);
}
