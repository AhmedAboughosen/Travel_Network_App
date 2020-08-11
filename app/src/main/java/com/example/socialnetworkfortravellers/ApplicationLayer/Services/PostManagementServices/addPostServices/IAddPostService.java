package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.addPostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

public interface IAddPostService {
    void setUpAddPostServiceCallBack(IAddPostServiceCallBack mAddPostServiceCallBack);

    void addPostContent( PostModel postModel);

}
