package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.GetAllCountryServices;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * responsibility of this class is to send request to Country List API  to get all country Name and Code and flag
 */
public class GetAllCountryService {


    private static GetAllCountryService ourInstance;


    public static GetAllCountryService getInstance() {
        return ourInstance = (ourInstance != null) ? ourInstance : new GetAllCountryService();
    }

    private GetAllCountryService() {
    }


    /**
     * get Countries from https://api.printful.com/countries API
     */
    public void getCountryInfo(Context context, IGetAllCountryServicesCallBack getAllCountryServicesCallBack) {
        try {
            ArrayList<CountryModel> list = new ArrayList<>();

            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(context);
            String url = "https://api.printful.com/countries";

            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    response -> {
                        try {
                            // get JSONObject from JSON file
                            if (!StringEmptyUtil.isEmptyString(response)) {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("result");

                                // looping through All Country
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject c = jsonArray.getJSONObject(i);
                                    String code = c.getString("code");
                                    String name = c.getString("name");
                                    String flag = "https://www.countryflags.io/" + code + "/shiny/64.png";
                                    CountryModel cityItem = new CountryModel();
                                    cityItem.setCityName(name);
                                    cityItem.setFlag(flag);
                                    cityItem.setCode(code);
                                    if (cityItem.getCode().equals("LY")){
                                        list.add(0 , cityItem);
                                        continue;
                                    }
                                    list.add(cityItem);
                                }

                                getAllCountryServicesCallBack.getAllCountry(list);
                            } else {
                                getAllCountryServicesCallBack.noCountry(ConstantValues.SERVER_PROBLEM);
                            }

                        } catch (Exception ex) {
                            ex.printStackTrace();
                            getAllCountryServicesCallBack.noCountry(ConstantValues.SERVER_PROBLEM);
                        }

                    }, error -> getAllCountryServicesCallBack.noCountry(error.getMessage()));

            // Add the request to the RequestQueue.
            queue.add(stringRequest);


        } catch (Exception ex) {
            ex.printStackTrace();
            getAllCountryServicesCallBack.noCountry(ConstantValues.SOMETHING_WENT_WRONG);
        }

    }


    public void Nullable() {
        ourInstance = null;
    }

}
