package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices;

import com.google.firebase.storage.StorageReference;

public interface ISaveImageService {

    void setStorageReference(StorageReference mStorageReference);
    void setFileBytes(byte[] mFileBytes);
    void setSaveFileServiceCallBack(ISaveImageServiceCallBack saveFileServiceCallBack);
    void saveFile();
}
