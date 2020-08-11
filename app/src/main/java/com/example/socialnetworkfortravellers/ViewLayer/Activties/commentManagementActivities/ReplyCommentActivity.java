package com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.commentManagementInjector.ReplyCommentInjector;
import com.example.socialnetworkfortravellers.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.COMMENT_IMAGE_REPLY;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.COMMENT_POST;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.COMMENT_REPLY;
import static com.example.socialnetworkfortravellers.nodesLayer.CommentNode.LIKES;

public class ReplyCommentActivity extends BaseCommentActivity {

    public static final String COMMENT_KEY = "CommentKey";
    private String mBaseCommentKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_comment);

        try {
            ButterKnife.bind(this);
            setUpInject();
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbarTitle.setText(getString(R.string.Replies));
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            getData();
            setUpViews();
            setUpILikeAndDislikeToCommentPresenterCallback();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void getData() {
        if (getIntent().getStringExtra(COMMENT_KEY) != null) {
            mBaseCommentKey = getIntent().getStringExtra(COMMENT_KEY);
        }
    }

    private void setUpInject() {
        ReplyCommentInjector.getSharedInjector().injectIn(this);
    }


    /**
     * set Up List View.
     */
    protected void setUpListOfComment() {
        super.setUpListOfComment();
        mCommentAdapter.enableReplyFeature(false);
    }

    /**
     * setUpViews
     */
    protected void setUpViews() {
        super.setUpViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_REPLY).child(mBaseCommentKey);
        super.getAllComments(databaseReference);
    }

    @Override
    void updateRepliesState() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_POST).child(postModel.getPostKey()).child(mBaseCommentKey);
        mAddCommentPresenter.updateRepliesState(databaseReference, true);
    }

    @Override
    void onLikeClick(LikeModel likeModel, int index) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_REPLY).child(mBaseCommentKey).child(likeModel.getCommentKey()).child(LIKES);
        currentCommentIndex = index;
        mLikeAndDislikeToCommentPresenter.addLike(databaseReference);
    }

    @Override
    void onDisLikeClick(LikeModel likeModel, int index) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_REPLY).child(mBaseCommentKey).child(likeModel.getCommentKey()).child(LIKES).child(likeModel.getUserKey());
        currentCommentIndex = index;
        mLikeAndDislikeToCommentPresenter.removeLike(databaseReference);
    }


    @OnClick(R.id.send_comment)
    public void onSendComment() {
        startWaiting("Please wait,while we Adding your Comment.....", false);
        updateCommentModel();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(COMMENT_REPLY).child(mBaseCommentKey);
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child(COMMENT_IMAGE_REPLY).child(mBaseCommentKey);
        saveComment(databaseReference, storageReference);
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
