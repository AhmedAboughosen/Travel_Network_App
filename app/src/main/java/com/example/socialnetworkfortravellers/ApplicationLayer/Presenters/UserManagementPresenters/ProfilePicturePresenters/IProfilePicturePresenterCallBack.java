package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.IPresenterCallBack;

public interface IProfilePicturePresenterCallBack extends IPresenterCallBack {




    void saveImageUrl(String url,String message);


    void setUpCircleImage(String url);

    void setUpAddImageLayout();

}
