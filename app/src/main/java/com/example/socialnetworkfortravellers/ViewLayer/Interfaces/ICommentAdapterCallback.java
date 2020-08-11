package com.example.socialnetworkfortravellers.ViewLayer.Interfaces;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;

import java.util.ArrayList;
import java.util.List;

public interface ICommentAdapterCallback {

    void onLike(LikeModel likeModel, int index);

    void onDisLike(LikeModel likeModel, int index);


    void onLoveClick( ArrayList list_of_user);


    void onViewReplyClick( String commentKey);
}
