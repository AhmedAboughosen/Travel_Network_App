package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updatePostServices;

import java.util.HashMap;

public interface IUpdatePostService {

    void setPostContent(HashMap<String, Object> postContent);

    void updatePostContent(String userKey, String postKey);

    void setUpdatePostServiceCallBack(IUpdatePostServiceCallBack mUpdatePostServiceCallBack);
}
