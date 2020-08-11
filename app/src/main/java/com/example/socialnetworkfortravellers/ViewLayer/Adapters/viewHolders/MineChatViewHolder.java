package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MineChatViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.text)
    public TextView txtChatMessage;

    @BindView(R.id.time)
    public TextView txtChatMessageTime;

    public MineChatViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}