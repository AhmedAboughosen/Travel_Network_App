package com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices;

import android.net.Uri;

import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SaveFileService implements ISaveFileService {

    private StorageReference mStorageReference;
    private Uri mUriFile;
    private ISaveFileServiceCallBack mSaveFileServiceCallBack;


    @Override
    public void setStorageReference(StorageReference mStorageReference) {
        this.mStorageReference = mStorageReference;
    }


    @Override
    public void saveFile() {
        try {
            //store image  in Storage database section
            UploadTask uploadTask = mStorageReference.putFile(mUriFile);


            Task<Uri> urlTask = uploadTask.continueWithTask(task -> {
                try {
                    if (!task.isSuccessful()) {
                        mSaveFileServiceCallBack.failure(task.getException().getMessage());
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mSaveFileServiceCallBack.failure(ex.getMessage());
                }

                // Continue with the task to get the download URL
                return mStorageReference.getDownloadUrl();
            });


            //store image uri in data base section
            urlTask.addOnCompleteListener(task -> {
                try {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        mSaveFileServiceCallBack.urlFile(downloadUri);
                    } else {
                        mSaveFileServiceCallBack.failure(task.getException().getMessage());
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                    mSaveFileServiceCallBack.failure(ex.getMessage());
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
            mSaveFileServiceCallBack.failure(ex.getMessage());
        }

    }

    @Override
    public void setUrlFile(Uri urlFile) {
        this.mUriFile = urlFile;
    }

    @Override
    public void setSaveFileServiceCallBack(ISaveFileServiceCallBack saveFileServiceCallBack) {
        mSaveFileServiceCallBack = saveFileServiceCallBack;
    }
}
