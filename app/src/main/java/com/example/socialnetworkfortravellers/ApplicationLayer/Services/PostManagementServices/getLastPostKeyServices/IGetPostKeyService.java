package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getLastPostKeyServices;

public interface IGetPostKeyService {
    void getKeysOfPost(String userKey);

    void setGetLastPostKeyServiceCallBack(IGetPostKeyServiceCallBack getLastPostKeyServiceCallBack);
}
