package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.commentManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.CommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.DaggerCommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.DaggerReplyCommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.commentManagementComment.ReplyCommentComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities.CommentActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities.ReplyCommentActivity;

public class ReplyCommentInjector {


    private static ReplyCommentInjector mInjector;

    private ReplyCommentInjector() {
    }

    public static ReplyCommentInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ReplyCommentInjector();
        }
        return mInjector;
    }


    public void injectIn(ReplyCommentActivity replyCommentActivity) {
       ReplyCommentComponent commentComponent = DaggerReplyCommentComponent.builder()
                .contextModule(new ContextModule(replyCommentActivity))
                .build();

        commentComponent.inject(replyCommentActivity);

    }
}
