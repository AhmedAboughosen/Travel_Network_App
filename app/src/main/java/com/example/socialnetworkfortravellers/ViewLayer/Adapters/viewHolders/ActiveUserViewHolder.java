package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveUserViewHolder extends RecyclerView.ViewHolder {
    public @BindView(R.id.profile_image)
    CircleImageView profile_image;
    public @BindView(R.id.fullname)
    TextView fullname;
    public @BindView(R.id.bio)
    TextView bio;

    public @BindView(R.id.shimmer)
    ShimmerFrameLayout shimmer;

    public ActiveUserViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
