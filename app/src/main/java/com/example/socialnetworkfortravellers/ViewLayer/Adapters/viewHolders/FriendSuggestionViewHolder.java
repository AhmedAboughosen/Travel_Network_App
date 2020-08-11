package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class FriendSuggestionViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.profile_picture)
    CircleImageView profile_picture;

    public @BindView(R.id.full_name)
    TextView full_name;

    public @BindView(R.id.Bio)
    TextView mBio;

    public @BindView(R.id.follow_button)
    Button follow_button;

    public @BindView(R.id.number_of_Followers)
    TextView mNumberOfFollowers;
    public @BindView(R.id.card_view)
    CardView card_view;
    public @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;

    public FriendSuggestionViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
