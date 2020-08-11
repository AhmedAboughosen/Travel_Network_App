package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayTripViewHolders extends RecyclerView.ViewHolder {

    public @BindView(R.id.country_name)
    TextView mCountryName;
    public @BindView(R.id.date_time)
    TextView mDateTime;
    public @BindView(R.id.image_layout)
    LinearLayout mCountryImage;

    public DisplayTripViewHolders(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
