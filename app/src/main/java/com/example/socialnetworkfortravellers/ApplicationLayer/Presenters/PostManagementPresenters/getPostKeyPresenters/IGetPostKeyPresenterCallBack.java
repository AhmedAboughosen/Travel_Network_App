package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.getPostKeyPresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

import java.util.HashMap;

public interface IGetPostKeyPresenterCallBack extends IPresenterCallBack {

    void sortedPost(HashMap<String, Object> map);

    void noPostsExists();

}
