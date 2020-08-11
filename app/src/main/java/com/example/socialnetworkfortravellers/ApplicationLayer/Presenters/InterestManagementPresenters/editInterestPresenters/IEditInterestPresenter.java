package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;

import java.util.List;

public interface IEditInterestPresenter {
    void getInterestOfUser();

    void updateMarkedInterest(List<InterestModel> list);

    void setEditInterestPresenterCallBack(IEditInterestPresenterCallBack mEditInterestPresenterCallBack);

    void updateInterest(List<InterestModel> interestModels);
}
