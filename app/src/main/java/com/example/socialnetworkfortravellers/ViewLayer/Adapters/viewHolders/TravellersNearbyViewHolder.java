package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class TravellersNearbyViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.profile_Image)
    public CircleImageView mProfileImage;

    @BindView(R.id.country_flag)
    public CircleImageView mCountryFlag;

    @BindView(R.id.full_name)
    public TextView mFullNameTextView;

    public TravellersNearbyViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }


}
