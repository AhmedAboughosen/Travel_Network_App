package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addLikeToCommentServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.google.firebase.database.DatabaseReference;

public interface IAddLikeToCommentService {
    void addLikeToComment(DatabaseReference databaseReferencel);

    void setUpAddLikeToCommentServiceCallback(IAddLikeToCommentServiceCallback mAddLikeToCommentServiceCallback);
}
