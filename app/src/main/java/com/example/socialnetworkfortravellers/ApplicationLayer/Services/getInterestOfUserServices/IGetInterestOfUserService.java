package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getInterestOfUserServices;

public interface IGetInterestOfUserService {
    void setUpGetInterestOfUserServiceCallBack(IGetInterestOfUserServiceCallBack mGetInterestOfUserServiceCallBack);

    void getInterestOfUser(String userKey);

    void removeEventListener();
}
