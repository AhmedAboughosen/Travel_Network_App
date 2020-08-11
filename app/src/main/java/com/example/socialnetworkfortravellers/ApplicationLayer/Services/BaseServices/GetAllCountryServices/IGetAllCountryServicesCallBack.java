package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;

import java.util.List;

public interface IGetAllCountryServicesCallBack {

    void getAllCountry(List<CountryModel> list);

    void noCountry(String str);

}
