package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostKeyServices;

public interface IUpdatePostKeyService {
    void addNewPostKey(Object timestamp, String postKey, String userKey);

    void setUpdatePostKeyServiceCallBack(IUpdatePostKeyServiceCallBack mUpdatePostKeyServiceCallBack);
}
