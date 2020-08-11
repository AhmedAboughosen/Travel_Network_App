package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCounterSerivces;

public interface IGetUserCountService {

    void getUserCount(String userKey);

    void removeEventListener();
    void setUpGetUserCounterSerivceCallBack(IGetUserCountServiceCallBack mGetUserCounterSerivceCallBack);
}
