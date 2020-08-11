package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices;

import com.google.firebase.database.DatabaseReference;

public interface IGetAllCommentService {
    void getComments( DatabaseReference databaseReference );

    void setGetAllCommentServiceCallBack(IGetAllCommentServiceCallBack mGetAllCommentServiceCallBack);
}
