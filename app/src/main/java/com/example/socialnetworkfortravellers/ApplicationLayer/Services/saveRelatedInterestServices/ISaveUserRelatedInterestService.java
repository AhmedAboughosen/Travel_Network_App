package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveRelatedInterestServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface ISaveUserRelatedInterestService {
    void saveRelatedInterest(List<InterestModel> interestModels);
    void setUpSaveUserInterestServiceCallBack(ISaveUserRelatedInterestServiceCallBack mSaveUserInterestServiceCallBack);
}
