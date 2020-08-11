package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

public interface IAddCommentService {
    void saveComment(CommentModel commentModel , DatabaseReference databaseReference , StorageReference storageReference );
    void setUpAddCommentServiceCallBack(IAddCommentServiceCallBack mAddCommentServiceCallBack);
}
