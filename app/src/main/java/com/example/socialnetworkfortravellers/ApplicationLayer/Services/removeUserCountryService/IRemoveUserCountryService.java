package com.example.socialnetworkfortravellers.ApplicationLayer.Services.removeUserCountryService;

public interface IRemoveUserCountryService {

    void removeCountry(String userKey, String oldCountry);

    void setUpRemoveUserCountryServiceCallBack(IRemoveUserCountryServiceCallBack mGetUserCounterSerivceCallBack);
}
