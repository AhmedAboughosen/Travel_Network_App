package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.updateImagePostServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.removeDataServices.IRemoveDataServiceCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.savePostImageServices.ISavePostImageServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.PostNode;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.POST_IMAGES;

public class UpdateImagePostService implements IUpdateImagePostService {

    private IUpdateImagePostServiceCallBack mUpdateImagePostServiceCallBack;
    private String mUserKey, mPostKey;
    private ISavePostImageService mSavePostImageService;
    private List<String> mImagePaths;
    private int removeIndex;
    private List<Integer> deletedImage, mRemainingSpace;
    private IRemoveDataService mRemoveDataService;


    public UpdateImagePostService(ISavePostImageService savePostImageService, IRemoveDataService removeDataService) {
        this.mSavePostImageService = savePostImageService;
        this.mRemoveDataService = removeDataService;
        removeIndex = 0;
    }


    @Override
    public void updateImageOfPost(List<Integer> remainingSpace, List<Integer> deletedImage, List<String> imagePaths, String PostKey, String userKey) {
        this.deletedImage = deletedImage;
        this.mUserKey = userKey;
        this.mPostKey = PostKey;
        this.mImagePaths = imagePaths;
        this.mRemainingSpace = remainingSpace;
        if (this.deletedImage.size() != 0) {
            deleteImages();
        } else {
            saveNewImage();
        }
    }


    @Override
    public void deleteImages(List<Integer> mDeletedImage, String PostKey, String userKey) {
        mPostKey = PostKey;
        mUserKey = userKey;
        this.deletedImage = mDeletedImage;
        if (removeIndex < mDeletedImage.size()) {
            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(POST_IMAGES).child(userKey).child(PostKey).child(PostKey + mDeletedImage.get(removeIndex));
            removeIndex++;
            // Delete the file
            storageRef.delete().addOnSuccessListener(aVoid -> {
                // File deleted successfully
                deleteImages(mDeletedImage, PostKey, userKey);
            }).addOnFailureListener(exception -> {
                // Uh-oh, an error occurred!
                deleteImages(mDeletedImage, PostKey, userKey);
                mUpdateImagePostServiceCallBack.showMessageError(exception.getMessage());
            });
        } else {
            removeIndex = 0;
            deletePathOfImageWithoutSaveNewImage();
        }
    }

    private void deleteImages() {
        if (removeIndex < deletedImage.size()) {
            // Create a storage reference from our app
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(POST_IMAGES).child(mUserKey).child(mPostKey).child(mPostKey + deletedImage.get(removeIndex));
            removeIndex++;
            // Delete the file
            storageRef.delete().addOnSuccessListener(aVoid -> {
                // File deleted successfully
                deleteImages();
            }).addOnFailureListener(exception -> {
                // Uh-oh, an error occurred!
                deleteImages();
                mUpdateImagePostServiceCallBack.showMessageError(exception.getMessage());
            });
        } else {
            removeIndex = 0;
            deletePathOfImageWithSaveNewImage();
        }
    }


    private void deletePathOfImageWithSaveNewImage() {
        if (removeIndex < deletedImage.size()) {
            DatabaseReference storageRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(mUserKey).child(mPostKey).child(PostNode.POST_IMAGES).child("image_" + deletedImage.get(removeIndex));
            mRemoveDataService.setUpDatabaseReference(storageRef);
            ++removeIndex;
            mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
                @Override
                public void failure(String message) {
                    deletePathOfImageWithSaveNewImage();
                    mUpdateImagePostServiceCallBack.showMessageError(message);
                }

                @Override
                public void isSuccessful() {
                    deletePathOfImageWithSaveNewImage();
                }
            });
            mRemoveDataService.removeData();
        } else {
            saveNewImage();
        }
    }

    private void deletePathOfImageWithoutSaveNewImage() {
        if (removeIndex < deletedImage.size()) {
            DatabaseReference storageRef = FirebaseDatabase.getInstance().getReference().child(PostNode.POST).child(mUserKey).child(mPostKey).child(PostNode.POST_IMAGES).child("image_" + deletedImage.get(removeIndex));
            mRemoveDataService.setUpDatabaseReference(storageRef);
            ++removeIndex;
            mRemoveDataService.setUpRemoveDataServiceCallBack(new IRemoveDataServiceCallBack() {
                @Override
                public void failure(String message) {
                    deletePathOfImageWithoutSaveNewImage();
                    mUpdateImagePostServiceCallBack.showMessageError(message);
                }

                @Override
                public void isSuccessful() {
                    deletePathOfImageWithoutSaveNewImage();
                }
            });
            mRemoveDataService.removeData();
        } else {
            mUpdateImagePostServiceCallBack.updateImagePostSuccessful();
        }
    }


    private void saveNewImage() {
        setUpSavePostImageServiceCallBack();
        mSavePostImageService.saveImage(mRemainingSpace, mImagePaths, mPostKey, mUserKey);
    }

    private void setUpSavePostImageServiceCallBack() {
        mSavePostImageService.setUpSavePostImageServiceCallBack(new ISavePostImageServiceCallBack() {
            @Override
            public void showMessageError(String message) {
                mUpdateImagePostServiceCallBack.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mUpdateImagePostServiceCallBack.noInternetFound();
            }

            @Override
            public void saveImagePostSuccessful() {
                mUpdateImagePostServiceCallBack.updateImagePostSuccessful();
            }
        });
    }

    @Override
    public void setUpdateImagePostServiceCallBack(IUpdateImagePostServiceCallBack mUpdateImagePostServiceCallBack) {
        this.mUpdateImagePostServiceCallBack = mUpdateImagePostServiceCallBack;
    }
}
