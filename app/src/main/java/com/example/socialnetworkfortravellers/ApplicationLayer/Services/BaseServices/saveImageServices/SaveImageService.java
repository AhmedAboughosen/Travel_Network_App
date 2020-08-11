package com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices;

import android.content.Context;
import android.net.Uri;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * responsibility of this class is to save Image in database and return Uri of file in server.
 */
public class SaveImageService implements ISaveImageService {


    private StorageReference mStorageReference;
    private byte[] mFileBytes;
    private ISaveImageServiceCallBack mSaveFileServiceCallBack;
    private Context mContext;

    public SaveImageService(Context context) {
        this.mContext = context;
    }

    @Override
    public void setStorageReference(StorageReference mStorageReference) {
        this.mStorageReference = mStorageReference;
    }


    @Override
    public void saveFile() {
        try {

            if (!NetworkState.isNetworkConnected(this.mContext)) {
                mSaveFileServiceCallBack.failure(mContext.getResources().getString(R.string.no_internet));
                return;
            }

            //store image  in Storage database section
            UploadTask uploadTask = mStorageReference.putBytes(mFileBytes);


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
    public void setFileBytes(byte[] mFileBytes) {
        this.mFileBytes = mFileBytes;
    }

    @Override
    public void setSaveFileServiceCallBack(ISaveImageServiceCallBack saveFileServiceCallBack) {
        mSaveFileServiceCallBack = saveFileServiceCallBack;
    }
}
