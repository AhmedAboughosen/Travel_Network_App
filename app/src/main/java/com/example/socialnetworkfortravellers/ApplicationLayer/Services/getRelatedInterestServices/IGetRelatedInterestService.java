package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getRelatedInterestServices;

public interface IGetRelatedInterestService {
    void setUpGetRelatedInterestServiceCallBack(IGetRelatedInterestServiceCallBack mGetInterestOfUserServiceCallBack);

    void getUsersInInterest(String name);

}
