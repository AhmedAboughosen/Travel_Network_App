package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayInterestViewHolder extends RecyclerView.ViewHolder {

    public @BindView(R.id.interest_image)
    ImageView interest_image;
    public @BindView(R.id.interest_name)
    TextView interest_name;


    public DisplayInterestViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

    }
}
