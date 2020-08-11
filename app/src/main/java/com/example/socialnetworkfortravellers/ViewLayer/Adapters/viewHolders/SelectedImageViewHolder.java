package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectedImageViewHolder extends RecyclerView.ViewHolder {
    public @BindView(R.id.set_image)
    ImageView set_image;
    public @BindView(R.id.delete_image)
    ImageView delete_image;

    public SelectedImageViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
