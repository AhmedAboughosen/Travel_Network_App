package com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices;

public interface ICountriesListService {
    void setUpCountriesListServiceCallback(ICountriesListServiceCallback mCountriesListPresenterCallback);

    void getALLCountries();
}
