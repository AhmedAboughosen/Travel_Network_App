package com.example.socialnetworkfortravellers.eventBus;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;

public class PostEvent {


    private PostModel mPostModel;

    public PostEvent(PostModel postModel) {
        this.mPostModel = postModel;
    }

    public PostModel getPostModel() {
        return mPostModel;
    }
}
