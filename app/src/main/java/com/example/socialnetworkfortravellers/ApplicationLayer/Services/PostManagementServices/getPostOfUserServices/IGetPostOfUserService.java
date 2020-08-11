package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices;

public interface IGetPostOfUserService {
    void getPost(String userKey, String postKey);

    void setUpGetPostOfUserServiceCallBack(IGetPostOfUserServiceCallBack mGetPostOfUserServiceCallBack);
}
