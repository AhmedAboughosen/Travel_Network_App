package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices;

import android.net.Uri;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveImageServices.ISaveImageServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.ISaveFileService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.saveFileServices.ISaveFileServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.socialnetworkfortravellers.nodesLayer.PostNode.POST_IMAGES;

public class SavePostImageService implements ISavePostImageService {


    private ISaveImageService mSaveImageService;
    private int index = 0;
    private ICompressImageUtil mCompressImageUtil;
    private String postkey, userKey;
    private List<Uri> mUriList;
    private List<String> mImagePaths;
    private ISaveRawDataService mSaveRawDataService;
    private ISavePostImageServiceCallBack mSavePostImageServiceCallBack;
    private ISaveFileService mSaveFileService;
    private List<Integer> mSelectedImageID;

    public SavePostImageService(ISaveImageService saveImageService, ICompressImageUtil compressImageUtil, ISaveRawDataService saveRawDataService, ISaveFileService saveFileService) {
        this.mSaveImageService = saveImageService;
        this.mCompressImageUtil = compressImageUtil;
        this.mUriList = new ArrayList<>();
        this.mSaveRawDataService = saveRawDataService;
        this.mSaveFileService = saveFileService;
        this.mSelectedImageID = new ArrayList<>();
    }


    @Override
    public void saveImage(List<Integer> selectedImage, List<String> imagePaths, String postKey, String userKey) {
        this.postkey = postKey;
        this.mImagePaths = imagePaths;
        this.userKey = userKey;
        this.mSelectedImageID = selectedImage;
        saveImage();
    }


    private void saveImage() {
        Uri imageUri = Uri.fromFile(new File(mImagePaths.get(index)));

        byte[] data = mCompressImageUtil.compressImage(imageUri);
        if (data != null) {
            saveImageInDataBaseWithCompress(data);
        } else {
            saveImageInDataBaseWithoutCompress(imageUri);
            mSavePostImageServiceCallBack.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }
    }

    private void saveImageInDataBaseWithCompress(byte[] data) {
        mSaveImageService.setFileBytes(data);
        StorageReference PostsImageRef = FirebaseStorage.getInstance().getReference().child(POST_IMAGES).child(userKey).child(postkey).child(postkey + mSelectedImageID.get(index));
        mSaveImageService.setStorageReference(PostsImageRef);
        index++;
        mSaveImageService.setSaveFileServiceCallBack(new ISaveImageServiceCallBack() {
            @Override
            public void failure(String message) {
                if (index < mImagePaths.size()) {
                    saveImage();
                } else {
                    index = 0;
                    saveUrlOfImageInPostObject();
                }

                mSavePostImageServiceCallBack.showMessageError("Problem In Saving Image File into Database, please try later.  \n" + message);
            }

            @Override
            public void urlFile(Uri mUri) {
                mUriList.add(mUri);
                if (index < mImagePaths.size()) {
                    saveImage();
                } else {
                    index = 0;
                    saveUrlOfImageInPostObject();
                }
            }
        });

        mSaveImageService.saveFile();
    }


    private void saveImageInDataBaseWithoutCompress(Uri file) {
        mSaveFileService.setUrlFile(file);
        StorageReference PostsImageRef = FirebaseStorage.getInstance().getReference().child(POST_IMAGES).child(userKey).child(postkey).child(postkey + mSelectedImageID.get(index));
        mSaveFileService.setStorageReference(PostsImageRef);
        index++;
        mSaveFileService.setSaveFileServiceCallBack(new ISaveFileServiceCallBack() {
            @Override
            public void failure(String message) {
                if (index < mImagePaths.size()) {
                    saveImage();
                } else {
                    index = 0;
                    saveUrlOfImageInPostObject();
                }

                mSavePostImageServiceCallBack.showMessageError("Problem In Saving Image File into Database, please try later.  \n" + message);
            }

            @Override
            public void urlFile(Uri mUri) {
                mUriList.add(mUri);
                if (index < mImagePaths.size()) {
                    saveImage();
                } else {
                    index = 0;
                    saveUrlOfImageInPostObject();
                }
            }
        });

        mSaveFileService.saveFile();
    }


    private void saveUrlOfImageInPostObject() {
        if (index < mUriList.size()) {
            DatabaseReference mPostRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(userKey).child(postkey).child(POST_IMAGES).child("image_" + mSelectedImageID.get(index));

            mSaveRawDataService.setDatabaseReference(mPostRef);
            HashMap<String, Object> profileUrl = new HashMap<>();
            profileUrl.put("Image", mUriList.get(index).toString());
            ++index;
            mSaveRawDataService.setMapData(profileUrl);
            mSaveRawDataService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
                @Override
                public void showMessageError(String message) {
                    if (index < mUriList.size()) {
                        saveUrlOfImageInPostObject();
                    } else {
                        mSavePostImageServiceCallBack.saveImagePostSuccessful();
                    }

                    mSavePostImageServiceCallBack.showMessageError(message);
                }

                @Override
                public void noInternetFound() {
                    mSavePostImageServiceCallBack.noInternetFound();
                }

                @Override
                public void Successful() {

                    if (index < mUriList.size()) {
                        saveUrlOfImageInPostObject();
                    } else {
                        mSavePostImageServiceCallBack.saveImagePostSuccessful();
                    }
                }

            });

            mSaveRawDataService.updateData();
        }

    }

    @Override
    public void setUpSavePostImageServiceCallBack(ISavePostImageServiceCallBack mSavePostImageServiceCallBack) {
        this.mSavePostImageServiceCallBack = mSavePostImageServiceCallBack;
    }
}
