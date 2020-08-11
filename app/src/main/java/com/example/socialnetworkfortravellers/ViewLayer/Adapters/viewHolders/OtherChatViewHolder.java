package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class OtherChatViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.text)
    public TextView txtChatMessage;

    @BindView(R.id.profile_Image)
    public CircleImageView mProfileCircleImageView;

    @BindView(R.id.time)
    public TextView txtChatMessageTime;


    public OtherChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);


    }
}