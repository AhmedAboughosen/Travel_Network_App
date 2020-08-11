package com.example.socialnetworkfortravellers.ApplicationLayer.Services.getUserCountryServices;

public interface IGetUserCountryService {
    void getUserCountry(String countryName);

    void setUpGetUserCountryServiceCallBack(IGetUserCountryServiceCallBack mGetUserCountryServiceCallBack);
}
