package com.example.socialnetworkfortravellers.ApplicationLayer.Services.PostManagementServices.deletePostServices;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.POST_IMAGES;

public class DeleteImageOfPostService implements IDeleteImageOfPostService {


    private IDeleteImageOfPostServiceCallBack mDeleteImageOfPostServiceCallBack;
    private int index, NUMBER_OF_IMAGE = 4;

    public DeleteImageOfPostService() {

    }


    public void setDeleteImageOfPostServiceCallBack(IDeleteImageOfPostServiceCallBack mDeleteImageOfPostServiceCallBack) {
        this.mDeleteImageOfPostServiceCallBack = mDeleteImageOfPostServiceCallBack;
    }


    @Override
    public void deleteImageOfPost(String userKey, String postKey) {
        if (index < NUMBER_OF_IMAGE) {
            StorageReference storageRef = FirebaseStorage.getInstance().getReference().child(POST_IMAGES).child(userKey).child(postKey).child(postKey + index);
            ++index;
            // Delete the file
            storageRef.delete().addOnSuccessListener(aVoid -> {
                // File deleted successfully
                deleteImageOfPost(userKey, postKey);
            }).addOnFailureListener(exception -> {
                // Uh-oh, an error occurred!
                deleteImageOfPost(userKey, postKey);
            });
        } else {
            mDeleteImageOfPostServiceCallBack.deleteImageOfPostSuccessful();
        }
    }

}
