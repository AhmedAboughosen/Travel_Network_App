package com.example.socialnetworkfortravellers.ApplicationLayer.Services.notficastionServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IGetNewNotificationsService {
    void getNotifications(String userKey);
    void removeEventListener();
    void setUpBaseServiceCallBack(IBaseServiceCallBack baseServiceCallBack);
}
