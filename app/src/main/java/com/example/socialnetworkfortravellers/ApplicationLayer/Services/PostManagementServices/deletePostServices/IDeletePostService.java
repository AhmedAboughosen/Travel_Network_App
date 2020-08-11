package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices;

public interface IDeletePostService {
    void deletePost(String postKey, String userKey);

    void setUpDeletePostServiceCallBack(IDeletePostServiceCallBack mDeletePostServiceCallBack);

}
