package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;

import java.util.List;

public interface IEditInterestPresenterCallBack {
    void showMessageError(String message);

    void noInternetFound();

    void updateMarkedInterest(List<InterestModel> list);

    void getDataSuccessfully();

    void noItemSelected();
    void updateDataSuccessful();


}
