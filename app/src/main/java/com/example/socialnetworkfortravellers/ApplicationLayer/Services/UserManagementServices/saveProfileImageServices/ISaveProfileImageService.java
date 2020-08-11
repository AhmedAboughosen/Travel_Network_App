package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices;

public interface ISaveProfileImageService {
    void saveProfileImage(byte[] imageByte);

    void setSaveProfileImageServiceCallback(ISaveProfileImageServiceCallback mSaveProfileImageServiceCallback);
}
