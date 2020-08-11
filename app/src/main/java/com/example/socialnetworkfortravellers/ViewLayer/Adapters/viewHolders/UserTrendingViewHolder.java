package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserTrendingViewHolder extends RecyclerView.ViewHolder {


    @BindView(R.id.flexible_color)
    public LinearLayout flexible_color;

    @BindView(R.id.profile_image)
    public   CircleImageView profile_image;


    @BindView(R.id.fullname)
    public   TextView fullname;


    @BindView(R.id.bio)
    public  TextView mBioTextView;


    @BindView(R.id.location)
    public  TextView mLocationTextView;


    @BindView(R.id.Joined_date)
    public  TextView mJoinedDateTextView;



    @BindView(R.id.card_view)
    public  CardView card_view;


    public UserTrendingViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
