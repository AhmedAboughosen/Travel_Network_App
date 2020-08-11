package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters;

public interface ICountriesListPresenter {

    void getALLCountries();

    void setUpCountriesListPresenterCallBack(ICountriesListPresenterCallBack countriesListPresenterCallBack);
}
