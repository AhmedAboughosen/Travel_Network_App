package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices.ICountriesListServiceCallback;

import java.util.List;

public class CountriesListPresenter implements ICountriesListPresenter {


    private ICountriesListPresenterCallBack mCountriesListPresenterCallBack;
    private ICountriesListService mCountriesListService;

    public CountriesListPresenter(ICountriesListService countriesListService) {
        this.mCountriesListService = countriesListService;
        setUpCountriesListServiceCallback();
    }


    @Override
    public void setUpCountriesListPresenterCallBack(ICountriesListPresenterCallBack countriesListPresenterCallBack) {
        this.mCountriesListPresenterCallBack = countriesListPresenterCallBack;
    }


    @Override
    public void getALLCountries() {
        mCountriesListService.getALLCountries();
    }


    private void setUpCountriesListServiceCallback() {
        mCountriesListService.setUpCountriesListServiceCallback(new ICountriesListServiceCallback() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                mCountriesListPresenterCallBack.getAllCountry(list);
            }

            @Override
            public void noCountry(String str) {
                mCountriesListPresenterCallBack.noCountry(str);
            }
        });
    }

}
