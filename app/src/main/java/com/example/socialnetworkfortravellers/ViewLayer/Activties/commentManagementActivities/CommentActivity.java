package com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.commentManagementInjector.CommentInjector;
import com.example.socialnetworkfortravellers.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.COMMENT_IMAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.COMMENT_POST;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.LIKES;

public class CommentActivity extends BaseCommentActivity {

    public static final String POST_MODEL = "PostModel";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.comment_activity);
        try {
            ButterKnife.bind(this);
            setUpInject();
            setUpViews();
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbarTitle.setText(getString(R.string.comments));
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            setUpILikeAndDislikeToCommentPresenterCallback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setUpInject() {
        CommentInjector.getSharedInjector().injectIn(this);
    }


    /**
     * setUpViews
     */
    protected void setUpViews() {
        super.setUpViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_POST).child(postModel.getPostKey());
        super.getAllComments(databaseReference);
    }

    @Override
    void updateRepliesState() {
        Log.e("updateRepliesState", "null");
    }

    @Override
    void onLikeClick(LikeModel likeModel, int index) {
        currentCommentIndex = index;
        DatabaseReference mLikeToCommentReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_POST).child(likeModel.getPostKey()).child(likeModel.getCommentKey()).child(LIKES);
        mLikeAndDislikeToCommentPresenter.addLike(mLikeToCommentReference);
    }

    @Override
    void onDisLikeClick(LikeModel likeModel, int index) {
        DatabaseReference mDisLikeToCommentReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_POST).child(likeModel.getPostKey()).child(likeModel.getCommentKey()).child(LIKES).child(likeModel.getUserKey());
        currentCommentIndex = index;
        mLikeAndDislikeToCommentPresenter.removeLike(mDisLikeToCommentReference);
    }


    @OnClick(R.id.send_comment)
    public void onSendComment() {
        startWaiting("Please wait,while we Adding your Comment.....", false);
        updateCommentModel();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_POST).child(mCommentModel.getPostKey());
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(COMMENT_IMAGE).child(mCommentModel.getPostKey());
        saveComment(databaseReference, storageReference);
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
