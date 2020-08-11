package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;

import java.util.List;

public interface ICountriesListPresenterCallBack {

    void getAllCountry(List<CountryModel> list);
    void noCountry(String str);
}
