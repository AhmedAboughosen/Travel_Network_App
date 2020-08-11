package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.commentManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.CommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.DaggerCommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities.CommentActivity;

public class CommentInjector {


    private static CommentInjector mInjector;

    private CommentInjector() {
    }

    public static CommentInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new CommentInjector();
        }
        return mInjector;
    }


    public void injectIn(CommentActivity addCommentActivity) {
        CommentComponent commentComponent = DaggerCommentComponent.builder()
                .contextModule(new ContextModule(addCommentActivity))
                .build();

        commentComponent.setUpInject(addCommentActivity);

    }
}
