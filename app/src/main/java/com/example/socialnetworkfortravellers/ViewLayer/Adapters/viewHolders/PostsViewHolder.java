package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.media.Image;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.ReadMoreTextView;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class PostsViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.profile_image)
    CircleImageView profile_image;
    public @BindView(R.id.fullname)
    TextView fullname;

    public @BindView(R.id.overflow_menu)
    ImageButton overflow_menu;


    public @BindView(R.id.details)
    TextView details;
    public @BindView(R.id.content_layout)
    RelativeLayout dynamic_layout;


    public @BindView(R.id.likesImageView)
    ImageView likesImageView;
    public @BindView(R.id.likesContainer)
    LinearLayout likesContainer;
    public @BindView(R.id.likeCounterTextView)
    TextView likeCounterTextView;
    public @BindView(R.id.number_of_likes)
    TextView mNumberOfLikesTextView;
    public @BindView(R.id.commentsCountImageView)
    ImageView commentsCountImageView;
    public @BindView(R.id.commentsCountTextView)
    TextView commentsCountTextView;
    public @BindView(R.id.commentsCounterContainer)
    LinearLayout commentsCounterContainer;
    public @BindView(R.id.timeTextView)
    TextView dateTextView;

    public @BindView(R.id.number_of_comments)
    TextView mNumber_of_comments;

    public @BindView(R.id.shareContainer)
    LinearLayout shareContainer;

    public @BindView(R.id.shimmer)
    ShimmerFrameLayout mShimmerFrameLayout;

    public PostsViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
