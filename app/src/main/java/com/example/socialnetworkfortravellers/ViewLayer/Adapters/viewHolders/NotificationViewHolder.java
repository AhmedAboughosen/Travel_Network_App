package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.facebook.shimmer.ShimmerFrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class NotificationViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.profile_image)
    CircleImageView profile_picture;

    @BindView(R.id.message)
    public TextView mMessageTextView;

    @BindView(R.id.date)
    public TextView Joined_date;
    @BindView(R.id.shimmer)
    public ShimmerFrameLayout mShimmerFrameLayout;

    public NotificationViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
