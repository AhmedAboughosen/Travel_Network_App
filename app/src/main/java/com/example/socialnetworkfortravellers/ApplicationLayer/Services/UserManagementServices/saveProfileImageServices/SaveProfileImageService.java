package com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices;

import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.InfrastructureLayer.ConstantValues;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.PROFILE_IMAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.UserNode.USER;

public class SaveProfileImageService implements ISaveProfileImageService {

    private ISaveProfileImageServiceCallback mSaveProfileImageServiceCallback;
    private ISaveImageService mSaveFileService;
    private ISaveRawDataService mSaveRawDataService;


    public SaveProfileImageService(ISaveImageService saveFileService, ISaveRawDataService saveRawDataService) {
        this.mSaveFileService = saveFileService;
        this.mSaveRawDataService = saveRawDataService;
    }


    /**
     * this method used to save profile image in firebase storage.
     *
     * @param imageByte
     */
    @Override
    public void saveProfileImage(byte[] imageByte) {

        if (imageByte != null) {
            saveImageInDataBase(imageByte);
        } else {
            mSaveProfileImageServiceCallback.showMessageError(ConstantValues.SOMETHING_WENT_WRONG);
        }
    }


    /**
     * save image in firebase storage.
     *
     * @param data
     */
    private void saveImageInDataBase(byte[] data) {
        mSaveFileService.setFileBytes(data);
        StorageReference mUserProfileImageRef = FirebaseStorage.getInstance().getReference().child(PROFILE_IMAGE).child(CurrentUserIDUtil.getInstance().getCurrentUserID() + ".png");
        mSaveFileService.setStorageReference(mUserProfileImageRef);
        mSaveFileService.setSaveFileServiceCallBack(new ISaveImageServiceCallBack() {

            @Override
            public void failure(String message) {
                mSaveProfileImageServiceCallback.showMessageError(message);
            }

            @Override
            public void urlFile(Uri mUri) {
                saveUrlinDataBase(mUri);
            }
        });
        mSaveFileService.saveFile();

    }


    /**
     * this method used to save  profile Uri in user data
     *
     * @param mUri
     */
    private void saveUrlinDataBase(Uri mUri) {
        DatabaseReference mUsersRef = FirebaseDatabase.getInstance().getReference().child(USER).child(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mSaveRawDataService.setDatabaseReference(mUsersRef);
        HashMap<String, Object> profileUrl = new HashMap<>();
        profileUrl.put(PROFILE_IMAGE, mUri.toString());
        mSaveRawDataService.setMapData(profileUrl);
        mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mSaveProfileImageServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mSaveProfileImageServiceCallback.networkErrorMessage();
            }

            @Override
            public void Successful() {
                mSaveProfileImageServiceCallback.uploadProfileImageSuccessful("Profile Image Uploaded successfully", mUri.toString());
            }

        });

        mSaveRawDataService.updateData();
    }


    public void setSaveProfileImageServiceCallback(ISaveProfileImageServiceCallback mSaveProfileImageServiceCallback) {
        this.mSaveProfileImageServiceCallback = mSaveProfileImageServiceCallback;
    }
}
