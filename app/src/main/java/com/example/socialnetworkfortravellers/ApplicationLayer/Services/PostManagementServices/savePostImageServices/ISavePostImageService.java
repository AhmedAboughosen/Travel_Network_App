package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices;

import java.util.List;

public interface ISavePostImageService {
    void saveImage(List<Integer> mSelectedImage , List<String> imagePaths, String postkey, String userKey);

    void setUpSavePostImageServiceCallBack(ISavePostImageServiceCallBack mSavePostImageServiceCallBack);
}
