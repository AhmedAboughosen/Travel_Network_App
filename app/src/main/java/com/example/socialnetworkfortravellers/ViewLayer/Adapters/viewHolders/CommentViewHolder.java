package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommentViewHolder {


    public @BindView(R.id.profile_image)
    CircleImageView mProfileImage;

    public @BindView(R.id.user_comment_fullname)
    TextView mFullNameTextView;

    public @BindView(R.id.comment)
    TextView mCommentTextView;

    public @BindView(R.id.ImageComment)
    ImageView mImageCommentImageView;

    public @BindView(R.id.date_time)
    TextView mCommentDateTextView;

    public @BindView(R.id.reply)
    TextView mReplyTextView;

    public @BindView(R.id.likes)
    TextView mLikesTextView;

    public @BindView(R.id.love_icon)
    ImageView mLoveIocnImageView;

    public @BindView(R.id.number_of_likes)
    TextView mNumberOfLikeTextView;


    public @BindView(R.id.view_replies)
    TextView mViewRepliesTextView;


    public CommentViewHolder(View view) {
        ButterKnife.bind(this, view);
    }
}
