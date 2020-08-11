package com.example.socialnetworkfortravellers.ApplicationLayer.Services.CountriesListServices;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices.GetAllCountryService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices.IGetAllCountryServicesCallBack;

import java.util.List;

public class CountriesListService implements ICountriesListService {


    private Context mContext;
    private ICountriesListServiceCallback mCountriesListServiceCallback;


    public CountriesListService(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * get ALL Countries
     */
    @Override
    public void getALLCountries() {
        GetAllCountryService.getInstance().getCountryInfo(mContext, new IGetAllCountryServicesCallBack() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                mCountriesListServiceCallback.getAllCountry(list);
            }

            @Override
            public void noCountry(String str) {
                mCountriesListServiceCallback.noCountry(str);
            }
        });
    }


    /**
     * setUpCountriesListPresenterCallback
     *
     * @param mCountriesListPresenterCallback
     */
    @Override
    public void setUpCountriesListServiceCallback(ICountriesListServiceCallback mCountriesListPresenterCallback) {
        this.mCountriesListServiceCallback = mCountriesListPresenterCallback;
    }
}
