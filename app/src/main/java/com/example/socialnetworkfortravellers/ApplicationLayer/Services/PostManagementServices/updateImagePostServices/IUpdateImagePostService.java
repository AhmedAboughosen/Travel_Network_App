package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices;

import java.util.List;

public interface IUpdateImagePostService {
    void updateImageOfPost(List<Integer> remainingSpace, List<Integer> deletedImage, List<String> imagePaths, String PostKey, String userKey);

    void setUpdateImagePostServiceCallBack(IUpdateImagePostServiceCallBack mUpdateImagePostServiceCallBack);

    void deleteImages(List<Integer> mDeletedImage , String PostKey, String userKey);
}
