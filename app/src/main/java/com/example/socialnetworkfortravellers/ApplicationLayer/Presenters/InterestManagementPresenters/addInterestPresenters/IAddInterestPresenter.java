package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;

import java.util.List;

public interface IAddInterestPresenter {
    void addInterest(List<InterestModel> interestModels);

    void setUpAddInterestPresenterCallBack(IAddInterestPresenterCallBack mAddInterestPresenterCallBack);
}
