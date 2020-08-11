package com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatedFollowAndUnFollowServices;

public interface IUpdateFollowingService {
    void updateFollowing(String mFriendKey);

    void setUpUpdateFollowingServiceCallBack(IUpdateFollowingServiceCallBack mUpdateFollowingServiceCallBack);
}
