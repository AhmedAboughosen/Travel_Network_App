package com.example.socialnetworkfortravellers.ApplicationLayer.Services.commentManagementServices.addCommentServices;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.IBaseServiceCallBack;

public interface IAddCommentServiceCallBack extends IBaseServiceCallBack {
    void addCommentSuccessful(CommentModel commentModel);
}
