package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessengerViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.profile_picture)
    public CircleImageView mProfileImageCircleImageView;

    @BindView(R.id.full_name)
    public TextView mFullNameTextView;

    @BindView(R.id.last_message)
    public TextView mLastMessageTextView;

    @BindView(R.id.time)
    public TextView mTextViewTime;

    public MessengerViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
