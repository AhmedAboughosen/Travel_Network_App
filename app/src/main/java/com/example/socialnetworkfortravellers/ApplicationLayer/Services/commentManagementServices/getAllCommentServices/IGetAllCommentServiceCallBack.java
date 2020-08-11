package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.getAllCommentServices;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;

import java.util.List;

public interface IGetAllCommentServiceCallBack {

    void getAllComments(List<CommentModel> commentModels);

    void onCancelled(@NonNull String databaseError);

    void noInternetFound();
}
