package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices;

public interface IDeleteImageOfPostService {
    void deleteImageOfPost(String userKey, String postKey);
    void setDeleteImageOfPostServiceCallBack(IDeleteImageOfPostServiceCallBack mDeleteImageOfPostServiceCallBack);
}
