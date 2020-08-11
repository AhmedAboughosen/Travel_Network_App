package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices;

import androidx.annotation.NonNull;

import java.util.HashMap;

public interface IGetPostKeyServiceCallBack {

    void postKeys(HashMap<String, Object> map);

    void showMessageError(@NonNull String databaseError);

    void noInternetFound();
}
