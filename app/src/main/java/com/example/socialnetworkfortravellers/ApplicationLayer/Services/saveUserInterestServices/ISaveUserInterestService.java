package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveUserInterestServices;

import java.util.HashMap;

public interface ISaveUserInterestService {
    void saveInterest(HashMap<String, Object> interestMap);
    void setUpSaveUserInterestServiceCallBack(ISaveUserInterestServiceCallBack mSaveUserInterestServiceCallBack);
}
