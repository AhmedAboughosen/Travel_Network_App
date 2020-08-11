package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface ISaveProfileImageServiceCallback extends IPresenterCallBack {
    void uploadProfileImageSuccessful(String message , String url);

}
