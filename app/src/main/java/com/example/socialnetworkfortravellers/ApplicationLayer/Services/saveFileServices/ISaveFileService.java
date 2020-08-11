package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices;

import android.net.Uri;

import com.google.firebase.storage.StorageReference;

public interface ISaveFileService {
    void setStorageReference(StorageReference mStorageReference);

    void setUrlFile(Uri file);

    void setSaveFileServiceCallBack(ISaveFileServiceCallBack saveFileServiceCallBack);

    void saveFile();
}
