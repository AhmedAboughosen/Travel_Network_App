package com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters.IAddCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.addCommentPresenters.IAddCommentPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters.IDisPlayCommentPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.displayCommentPresenters.IDisplayCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters.ILikeAndDislikeToCommentPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.commentManagementPresenters.likeAndDislikeToCommentPresenters.ILikeAndDislikeToCommentPresenterCallback;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.FilePickerActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.CommentAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICommentAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import gun0912.tedbottompicker.TedBottomPicker;

public abstract class BaseCommentActivity extends FilePickerActivity {

    public static final String POST_MODEL = "PostModel";

    @Inject
    CommentModel mCommentModel;
    @Inject
    UserSharedPrefManager mUserSharedPrefManager;
    @Inject
    IDisplayCommentPresenter mDisPlayCommentPresenter;
    @Inject
    IAddCommentPresenter mAddCommentPresenter;
    @Inject
    ILikeAndDislikeToCommentPresenter mLikeAndDislikeToCommentPresenter;

    protected @BindView(R.id.send_comment)
    ImageView mSendCommentImageView;
    protected @BindView(R.id.add_image_to_comment)
    RelativeLayout mRelativeLayout;
    protected @BindView(R.id.post_comment)
    EditText mPostCommentEditText;
    protected @BindView(R.id.display_all_comments_RecyclerView)
    LinearLayout mListOfCommentsLinearLayout;
    protected @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    protected @BindView(R.id.comment_image_card_view)
    CardView mCardView;
    protected @BindView(R.id.toolbar)
    Toolbar mToolbar;
    protected @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    protected CommentAdapter mCommentAdapter;
    protected SwipeMenuListView mSwipeMenuListView;
    protected int currentCommentIndex;
    protected PostModel postModel;

    protected void setUpViews() {
        postModel = (PostModel) getIntent().getExtras().getSerializable(POST_MODEL);
        mSendCommentImageView.setVisibility(View.GONE);
    }


    abstract void updateRepliesState();

    abstract void onLikeClick(LikeModel likeModel, int index);

    abstract void onDisLikeClick(LikeModel likeModel, int index);


    /**
     * get all comments
     */
    protected void getAllComments(DatabaseReference databaseReference) {
        mDisPlayCommentPresenter.setDisPlayCommentPresenter(new IDisPlayCommentPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BaseCommentActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void getAllComments(List<CommentModel> commentModels) {
                mProgressBar.setVisibility(View.GONE);
                setUpListOfComment();
                mCommentAdapter.clear();
                mCommentAdapter.addAll(commentModels);
                runLayoutAnimation(mSwipeMenuListView);
            }

            @Override
            public void noInternetFound() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BaseCommentActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void thereIsNoCommentForThisThread() {
                mProgressBar.setVisibility(View.GONE);
                mListOfCommentsLinearLayout.removeAllViews();
                View noCommentView = LayoutInflater.from(BaseCommentActivity.this).inflate(R.layout.item_no_comment_layout, null);
                mListOfCommentsLinearLayout.addView(noCommentView);
            }
        });

        mDisPlayCommentPresenter.getAllComments(databaseReference);
    }


    /**
     * set Up List View.
     */
    protected void setUpListOfComment() {
        mCommentAdapter = new CommentAdapter(new ArrayList<>(), this);
        setUpSwipeMenuListView();
        onMenuItemClick();
        setCommentAdapterCallback();
    }


    /**
     * runLayoutAnimation
     *
     * @param recyclerView
     */
    protected void runLayoutAnimation(final SwipeMenuListView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }


    /**
     * on Menu Item Click
     */
    private void onMenuItemClick() {
        mSwipeMenuListView.setOnMenuItemClickListener((position, menu, index) -> {
            switch (index) {
                case 0:
                    /*
                     edit
                     */
                    Toast.makeText(BaseCommentActivity.this, "edit " + position, Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(BaseCommentActivity.this, "delete " + position, Toast.LENGTH_SHORT).show();
                    // delete
                    break;
            }
            // false : close the menu; true : not close the menu
            return false;
        });
    }


    /**
     * setUp Swipe Menu Creator
     */
    private void setUpSwipeMenuListView() {

        mSwipeMenuListView = new SwipeMenuListView(getApplicationContext());
        mSwipeMenuListView.setLayoutParams(
                new ViewGroup.LayoutParams(
                        // or ViewGroup.LayoutParams.WRAP_CONTENT
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        // or ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.MATCH_PARENT));

        SwipeMenuCreator creator = menu -> {
            // Create different menus depending on the view type
            switch (menu.getViewType()) {
                case 0:
                    setUpMenuForCurrentUser(menu);
                    break;
                case 1:
                    setUpMenuForVisitorUser(menu);
                    break;
            }
        };

        mSwipeMenuListView.setMenuCreator(creator);
        mSwipeMenuListView.setSwipeDirection(SwipeMenuListView.DIRECTION_LEFT);
        mSwipeMenuListView.setAdapter(mCommentAdapter);
        mListOfCommentsLinearLayout.removeAllViews();
        mListOfCommentsLinearLayout.addView(mSwipeMenuListView);
    }


    /**
     * setUpMenuForVisitorUser
     *
     * @param menu
     */
    private void setUpMenuForVisitorUser(SwipeMenu menu) {

        // create "reportItem" item
        SwipeMenuItem reportItem = new SwipeMenuItem(
                getApplicationContext());

        // set item width
        reportItem.setWidth(90);

        // set a icon
        reportItem.setIcon(R.drawable.ic_question_circle);

        menu.addMenuItem(reportItem);
    }


    /**
     * setUp Menu For current user
     */
    private void setUpMenuForCurrentUser(SwipeMenu menu) {
        // create "deleteItem" item
        SwipeMenuItem deleteItem = new SwipeMenuItem(
                getApplicationContext());

        // set item width
        deleteItem.setWidth(90);

        // set a icon
        deleteItem.setIcon(R.drawable.ic_delete_circle);

        menu.addMenuItem(deleteItem);
    }


    /**
     * on text change
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @OnTextChanged(R.id.post_comment)
    public void onCommentTextChange(@NotNull CharSequence s, int start, int before, int count) {
        mSendCommentImageView.setVisibility(mCommentModel.getCommentImage() != null ? View.VISIBLE : (!StringEmptyUtil.isEmptyString(s.toString())) ? View.VISIBLE : View.GONE);
        mCommentModel.setCommentText(s.toString());
    }


    /**
     * when user click on camera icon click
     */
    @OnClick(R.id.post_image)
    public void onDisplayImage() {
        super.fileActivity();
    }

    /**
     * this method used to display image picker then show image in UI.
     */
    @Override
    public void onPickFile() {
        TedBottomPicker.with(BaseCommentActivity.this)
                .show(this::displayImage);
    }


    /**
     * when get uri from image picker , display it in UI.
     *
     * @param uri
     */
    private void displayImage(Uri uri) {
        // here is selected image uri
        View selectedImage = LayoutInflater.from(this).inflate(R.layout.item_selected_image_post, null);

        ImageView set_image = selectedImage.findViewById(R.id.set_image);
        ImageView delete_image = selectedImage.findViewById(R.id.delete_image);

        GlideUtil.loadImage(this, uri.toString(), set_image);


        mRelativeLayout.setVisibility(View.VISIBLE);
        mCardView.setVisibility(View.VISIBLE);


        mRelativeLayout.removeAllViews();
        mRelativeLayout.addView(selectedImage);
        mCommentModel.setCommentImage(uri);
        mSendCommentImageView.setVisibility(View.VISIBLE);

        delete_image.setOnClickListener(v -> {
            mCommentModel.setCommentImage(null);
            mSendCommentImageView.setVisibility(StringEmptyUtil.isEmptyString(mCommentModel.getCommentText()) ? View.GONE : View.VISIBLE);


            mRelativeLayout.removeAllViews();
            mRelativeLayout.setVisibility(View.GONE);
            mCardView.setVisibility(View.GONE);
        });
    }


    /**
     * save comment
     */
    protected void saveComment(DatabaseReference databaseReference, StorageReference storageReference) {
        mAddCommentPresenter.setUpAddCommentPresenterCallBack(new IAddCommentPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                finishWaiting();
                Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(getApplicationContext(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void saveCommentSuccessful(CommentModel commentModel) {
                finishWaiting();
                addNewCommentIntoList(commentModel);
                reSetUpViews();
                updateRepliesState();
            }

            @Override
            public void updateRepliesStateSuccessful() {
                finishWaiting();
                Log.e("CommentActivity", "updateRepliesStateSuccessful");
            }
        });


        mAddCommentPresenter.saveComment(mCommentModel, databaseReference, storageReference);
    }


    /**
     * updateCommentModel
     */
    protected void updateCommentModel() {
        mCommentModel.setPostKey(postModel.getPostKey());
        mCommentModel.setUserOfPostKey(postModel.getUserPostModel().getUserInfoModel().getKeyOfUser());
        mCommentModel.setUserKey(CurrentUserIDUtil.getInstance().getCurrentUserID());

        UserModel userModel = new UserModel();
        userModel.setFullName(mUserSharedPrefManager.getFullName());
        userModel.setProfilePicture(mUserSharedPrefManager.getImageUrl());
        mCommentModel.setUserModel(userModel);
    }

    /**
     * add a new comment into list at index 0
     *
     * @param commentModel
     */
    private void addNewCommentIntoList(CommentModel commentModel) {
        if (mSwipeMenuListView != null) {
            copyObject(commentModel);
        } else {
            setUpListOfComment();
            copyObject(commentModel);
        }
    }


    /**
     * clone object
     *
     * @param commentModel
     */
    private void copyObject(CommentModel commentModel) {
        CommentModel newComment;
        try {
            newComment = (CommentModel) commentModel.clone();
            mCommentAdapter.getListOfComment().add(0, newComment);
            mCommentAdapter.notifyDataSetChanged();
            runLayoutAnimation(mSwipeMenuListView);
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

    }


    /**
     * re setUp  View after add Comment
     */
    protected void reSetUpViews() {
        mCommentModel.setCommentImage(null);
        mCommentModel.setCommentText("");
        mPostCommentEditText.setText(mCommentModel.getCommentText());
        mSendCommentImageView.setVisibility(View.GONE);
        mRelativeLayout.removeAllViews();
        mRelativeLayout.setVisibility(View.GONE);
        mCardView.setVisibility(View.GONE);
    }


    /**
     * when user click on like or reply or dislike etc.
     */
    protected void setCommentAdapterCallback() {
        mCommentAdapter.setCommentAdapterCallback(new ICommentAdapterCallback() {
            @Override
            public void onLike(LikeModel likeModel, int index) {
                onLikeClick(likeModel, index);
            }

            @Override
            public void onDisLike(LikeModel likeModel, int index) {
                onDisLikeClick(likeModel, index);
            }

            @Override
            public void onLoveClick(ArrayList list_of_user) {
                ConfigUtil.allowToAddCurrentUser = true;
                Intent intent = new Intent(getApplicationContext(), DisplayListOfUserActivity.class);
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, list_of_user);
                intent.putExtra(DisplayListOfUserActivity.TITLE, "People who reacted");
                startActivity(intent);
                animateWithZoom();
            }

            @Override
            public void onViewReplyClick(String commentKey) {
                Intent intent = new Intent(getApplicationContext(), ReplyCommentActivity.class);
                intent.putExtra(ReplyCommentActivity.COMMENT_KEY, commentKey);
                intent.putExtra(BaseCommentActivity.POST_MODEL, postModel);

                startActivity(intent);
                animateWithZoom();
            }
        });
    }

    /**
     * setUpILikeAndDislikeToCommentPresenterCallback
     */
    protected void setUpILikeAndDislikeToCommentPresenterCallback() {
        mLikeAndDislikeToCommentPresenter.setUpILikeAndDislikeToCommentPresenterCallback(new ILikeAndDislikeToCommentPresenterCallback() {
            @Override
            public void showMessageError(String message) {
                Toast.makeText(BaseCommentActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                Toast.makeText(BaseCommentActivity.this, getText(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void addLikeSuccessful() {
                mCommentAdapter.addLikeStyleToComment(currentCommentIndex);
            }

            @Override
            public void removeLikeSuccessful() {
                mCommentAdapter.addDislikeStyleToComment(currentCommentIndex);
            }

        });
    }

}
