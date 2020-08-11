package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.tripManagementModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.CountriesListPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.ICountriesListPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.CountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;

import dagger.Module;
import dagger.Provides;

@Module
public class CountryListModules {

    @Provides
    ICountriesListPresenter ProvidesCountriesListPresenter(ICountriesListService countriesListService) {
        return new CountriesListPresenter(countriesListService);
    }

    @Provides
    ICountriesListService ProvidesCountriesListService(Context context) {
        return new CountriesListService(context);
    }
}
