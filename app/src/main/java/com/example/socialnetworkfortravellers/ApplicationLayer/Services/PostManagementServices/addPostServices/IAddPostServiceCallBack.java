package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IAddPostServiceCallBack extends IBaseServiceCallBack {
    void savePosSuccessful(String postKey);
}
