package com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;

import java.util.List;

public interface ICountriesListServiceCallback {
    void getAllCountry(List<CountryModel> list);

    void noCountry(String str);
}
