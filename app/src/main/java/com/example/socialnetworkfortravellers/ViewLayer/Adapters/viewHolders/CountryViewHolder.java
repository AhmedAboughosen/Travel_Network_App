package com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CountryViewHolder {


    @BindView(R.id.flag)
    ImageView flag;
    @BindView(R.id.country_name)
    TextView names;

    public CountryViewHolder(View view) {
        ButterKnife.bind(this, view);
    }


    public TextView getNames() {
        return names;
    }

    public void setNames(TextView names) {
        this.names = names;
    }

    public ImageView getFlag() {
        return flag;
    }

    public void setFlag(ImageView flag) {
        this.flag = flag;
    }
}