package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateNumberOfPostsServices;

public interface IUpdateNumberOfPostsService {
    void updateNumberOfPost(String userKey , boolean increase_or_decrease);

    void setUpUpdateNumberOfPostsServiceCallBack(IUpdateNumberOfPostsServiceCallBack mUpdateNumberOfPostsServiceCallBack);
}
