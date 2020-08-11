package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryListViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.country_name)
    TextView country_name;

    public @BindView(R.id.country_flag)
    ImageView country_flag;

    public CountryListViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
