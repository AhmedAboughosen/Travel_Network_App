package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters;

import android.content.Intent;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;

public interface IEditUserInformationPresenter {

    void getAllCountry(String oldCountry);

    void getFilePath(int requestCode, int resultCode, Intent data);

    void setUpEditUserInformationPresenterCallBack(IEditUserInformationPresenterCallBack mEditUserInformationPresenterCallBack);

    void updateUserInfo(UserModel userModel, boolean isProfilePictureChange);
}
