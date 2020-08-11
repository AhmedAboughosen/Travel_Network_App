package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addDisLikeToCommentService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.google.firebase.database.DatabaseReference;

public interface IAddDisLikeToCommentService {
    void setUpAddDisLikeToCommentService(IAddDisLikeToCommentServiceCallback mAddDisLikeToCommentService);

    void addDisLikeToComment(DatabaseReference databaseReference);
}
