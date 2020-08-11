package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CommentModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.LikeModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.CommentViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.DisplayImageDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICommentAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class CommentAdapter extends ArrayAdapter<CommentModel> {

    private List<CommentModel> mListOfComment;
    private Activity mContext;
    private final int menuTypeCount = 2;
    private int type = 1;
    private ICommentAdapterCallback mCommentAdapterCallback;
    private CommentViewHolder mCommentViewHolder;
    private boolean enabled = true;

    public CommentAdapter(List<CommentModel> commentModelList, Activity context) {
        super(context, R.layout.item_comment, commentModelList);
        mListOfComment = commentModelList;
        this.mContext = context;
    }


    public void enableReplyFeature(boolean enabled) {
        this.enabled = enabled;
    }


    public List<CommentModel> getListOfComment() {
        return mListOfComment;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CommentModel commentModel = mListOfComment.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        CommentViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_comment, parent, false);
            result = convertView;
            viewHolder = new CommentViewHolder(result);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (CommentViewHolder) convertView.getTag();
            result = convertView;
        }

        mCommentViewHolder = viewHolder;

        viewHolder.mReplyTextView.setVisibility((enabled) ? View.VISIBLE : View.GONE);
        /*
        user info
         */
        viewHolder.mFullNameTextView.setText((!StringEmptyUtil.isEmptyString(commentModel.getUserModel().getFullName())) ? commentModel.getUserModel().getFullName() : "None");
        if (!StringEmptyUtil.isEmptyString(commentModel.getUserModel().getProfilePicture())) {
            GlideUtil.loadImage(mContext, commentModel.getUserModel().getProfilePicture(), viewHolder.mProfileImage);
        } else {
            Glide.with(Objects.requireNonNull(mContext))
                    .load(R.drawable.user_image)
                    .into(viewHolder.mProfileImage);
        }

        /*
        comment Info
         */
        if (!StringEmptyUtil.isEmptyString(commentModel.getCommentText())) {
            viewHolder.mCommentTextView.setVisibility(View.VISIBLE);
            viewHolder.mCommentTextView.setText(commentModel.getCommentText());
        } else {
            viewHolder.mCommentTextView.setVisibility(View.GONE);
        }


        /*
         * Image of comment info
         */
        if (commentModel.getCommentImage() != null &&  !commentModel.getCommentImage().toString().isEmpty()) {
            viewHolder.mImageCommentImageView.setVisibility(View.VISIBLE);
            GlideUtil.loadImage(mContext, commentModel.getCommentImage().toString(), viewHolder.mImageCommentImageView);
        } else {
            viewHolder.mImageCommentImageView.setVisibility(View.GONE);
        }

        /*
         * check if has replies
         */
        if (commentModel.isHasReplies()) {
            viewHolder.mViewRepliesTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.mViewRepliesTextView.setVisibility(View.GONE);
        }


        /*
        set time of comment
         */
        if (commentModel.getTime() != null) {
            String current_date = ConvertTimeUtil.toTimeStamp(commentModel.getTime());
            viewHolder.mCommentDateTextView.setText(current_date);
        } else {
            viewHolder.mCommentDateTextView.setText("1 mins");
        }

        /*
         *check if current user like this comment or not
         */
        if (commentModel.isCurrentUserLikeComment()) {
            viewHolder.mLikesTextView.setText("liked");
            viewHolder.mLikesTextView.setTextColor(mContext.getResources().getColor(R.color.colorAccent));
        } else {
            viewHolder.mLikesTextView.setText("like");
            viewHolder.mLikesTextView.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
        }


        /*
         *check number of likes of current comment.
         */
        if (commentModel.getNumber_of_likes() > 0) {
            viewHolder.mLoveIocnImageView.setVisibility(View.VISIBLE);
            viewHolder.mNumberOfLikeTextView.setVisibility(View.VISIBLE);
            viewHolder.mNumberOfLikeTextView.setText(mListOfComment.get(position).getUsersLikesKey().size() + "");
        } else {
            viewHolder.mLoveIocnImageView.setVisibility(View.GONE);
            viewHolder.mNumberOfLikeTextView.setVisibility(View.GONE);
        }

        eventClick(viewHolder, position);
        return result;
    }

    @Override
    public int getViewTypeCount() {
        // menu type count
        return menuTypeCount;
    }

    @Override
    public int getItemViewType(int position) {
        // current menu type, if type = 1 this mean is object is for current user
        return type = mListOfComment.get(position).getUserKey().equals(CurrentUserIDUtil.getInstance().getCurrentUserID()) ? 0 : 1;
    }


    private void eventClick(CommentViewHolder commentViewHolder, int index) {
        commentViewHolder.mImageCommentImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DisplayImageDialog displayImageDialog = new DisplayImageDialog(mContext);
                displayImageDialog.ImageDialog(mListOfComment.get(index).getCommentImage().toString());
            }
        });
        commentViewHolder.mLikesTextView.setOnClickListener(v -> {

            /*
            store data into like object such as post key and user key and comment key.
             */
            LikeModel likeModel = new LikeModel();
            likeModel.setCommentKey(mListOfComment.get(index).getCommentKey());
            likeModel.setPostKey(mListOfComment.get(index).getPostKey());
            likeModel.setUserKey(CurrentUserIDUtil.getInstance().getCurrentUserID());

            /*
            check if user like or not
             */
            if (commentViewHolder.mLikesTextView.getText().toString().equals("like")) {
                mCommentAdapterCallback.onLike(likeModel, index);
            } else {
                mCommentAdapterCallback.onDisLike(likeModel, index);
            }
        });

        commentViewHolder.mLoveIocnImageView.setOnClickListener(v -> {
            /*
             *display activity of user
             */
            HashMap<String, Boolean> listOfKeys = mListOfComment.get(index).getUsersLikesKey();
            Set<String> keySet = listOfKeys.keySet();
            ArrayList list_of_user = new ArrayList<>(keySet);
            mCommentAdapterCallback.onLoveClick(list_of_user);
        });

        commentViewHolder.mReplyTextView.setOnClickListener(v -> mCommentAdapterCallback.onViewReplyClick(mListOfComment.get(index).getCommentKey()));
        commentViewHolder.mViewRepliesTextView.setOnClickListener(v -> mCommentAdapterCallback.onViewReplyClick(mListOfComment.get(index).getCommentKey()));
    }


    /**
     * change style of like comment
     */
    public void addLikeStyleToComment(int index) {
        mListOfComment.get(index).setCurrentUserLikeComment(true);
        mListOfComment.get(index).getUsersLikesKey().put(CurrentUserIDUtil.getInstance().getCurrentUserID(), true);
        mListOfComment.get(index).setNumber_of_likes(mListOfComment.get(index).getNumber_of_likes() + 1);

        notifyDataSetChanged();

    }


    /**
     * change style of dis like comment
     */
    public void addDislikeStyleToComment(int index) {
        mListOfComment.get(index).setCurrentUserLikeComment(false);

        mListOfComment.get(index).setNumber_of_likes(mListOfComment.get(index).getNumber_of_likes() - 1);

        mListOfComment.get(index).getUsersLikesKey().remove(CurrentUserIDUtil.getInstance().getCurrentUserID());

        notifyDataSetChanged();

    }


    /**
     * setCommentAdapterCallback
     *
     * @param mCommentAdapterCallback
     */
    public void setCommentAdapterCallback(ICommentAdapterCallback mCommentAdapterCallback) {
        this.mCommentAdapterCallback = mCommentAdapterCallback;
    }
}
