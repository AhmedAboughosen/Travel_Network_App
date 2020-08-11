package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;

import java.util.List;

public interface IBasePostPresenterCallBack {
    void setUpImageUrl(List<String> url);

    void showMessageError(String message);

    void getAllCountry(List<CountryModel> list);

    void noCountry(String str);
}
