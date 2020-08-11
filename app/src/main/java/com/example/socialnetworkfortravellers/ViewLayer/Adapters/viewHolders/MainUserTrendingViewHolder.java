package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainUserTrendingViewHolder extends RecyclerView.ViewHolder {


    public @BindView(R.id.list_of_trending_users)
    RecyclerView recyclerview;

    public MainUserTrendingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
