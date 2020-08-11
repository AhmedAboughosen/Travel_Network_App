package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.getPostOfUserServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IGetPostOfUserServiceCallBack extends IBaseServiceCallBack {

    void newPost(PostModel postModel);

    void postDoesNotExists();

}
