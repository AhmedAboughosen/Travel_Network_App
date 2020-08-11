package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters;

import android.content.Intent;

public interface IProfilePicturePresenter {

    void getFilePath(int requestCode, int resultCode, Intent data);

    void setProfilePicturePresenterCallBack(IProfilePicturePresenterCallBack mProfilePicturePresenterCallBack);

    void saveProfileImage(String saveImage);
}
