package com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.saveProfileImageServices.ISaveProfileImageServiceCallback;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import java.io.File;
import java.util.List;
import java.util.Objects;

import droidninja.filepicker.FilePickerConst;

public class ProfilePicturePresenter implements IProfilePicturePresenter {


    private IProfilePicturePresenterCallBack mProfilePicturePresenterCallBack;
    private ISaveProfileImageService mSaveProfileImageService;
    private ICompressImageUtil mCompressImageUtil;


    public ProfilePicturePresenter(ICompressImageUtil compressImageUtil, ISaveProfileImageService saveProfileImageService) {
        this.mSaveProfileImageService = saveProfileImageService;
        this.mCompressImageUtil = compressImageUtil;
        setSaveProfileImageServiceCallback();
    }


    /**
     * check if intent cotaint data or not if yes save data in list.
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void getFilePath(int requestCode, int resultCode, Intent data) {
        if (requestCode == ImagePickerActivity.CUSTOM_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            try {
                List<String> listOfImages = Objects.requireNonNull(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                if (listOfImages.size() >= 1) {
                    mProfilePicturePresenterCallBack.setUpCircleImage(listOfImages.get(0));
                } else {
                    mProfilePicturePresenterCallBack.setUpAddImageLayout();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mProfilePicturePresenterCallBack.showMessageError(ex.getMessage());
            }

        } else {
            mProfilePicturePresenterCallBack.setUpAddImageLayout();
        }
    }


    @Override
    public void setProfilePicturePresenterCallBack(IProfilePicturePresenterCallBack mProfilePicturePresenterCallBack) {
        this.mProfilePicturePresenterCallBack = mProfilePicturePresenterCallBack;
    }


    @Override
    public void saveProfileImage(String saveImage) {

        if (saveImage != null && !saveImage.isEmpty()) {
            Uri imageUri = Uri.fromFile(new File(saveImage));

            byte[] data = mCompressImageUtil.compressImage(imageUri);
            mSaveProfileImageService.saveProfileImage(data);
        } else {
            mProfilePicturePresenterCallBack.showMessageError("The Profile Image is Required");
        }
    }


    private void setSaveProfileImageServiceCallback() {
        mSaveProfileImageService.setSaveProfileImageServiceCallback(new ISaveProfileImageServiceCallback() {


            @Override
            public void uploadProfileImageSuccessful(String message, String url) {
                mProfilePicturePresenterCallBack.saveImageUrl(url,message);

            }

            @Override
            public void showMessageError(String message) {
                mProfilePicturePresenterCallBack.showMessageError(message);
            }

            @Override
            public void networkErrorMessage() {
                mProfilePicturePresenterCallBack.networkErrorMessage();
            }
        });
    }
}
