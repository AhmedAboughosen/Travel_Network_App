package com.example.socialnetworkfortravellers.ViewLayer.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.CountryViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * responsibility of this class is bind view with data of country list.
 */
public class CountryAdapter extends BaseAdapter {


    private Context context;
    private List<CountryModel> countryItems;
    private LayoutInflater inflter;

    public CountryAdapter(Context applicationContext, List<CountryModel> countryItems) {
        this.context = applicationContext;
        this.countryItems = countryItems;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return countryItems.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        try {

            CountryViewHolder countryViewHolder;

            if (view == null) {

                view = inflter.inflate(R.layout.item_name_of_country, viewGroup, false);
                countryViewHolder = new CountryViewHolder(view);
                view.setTag(countryViewHolder);
            } else {
                countryViewHolder = (CountryViewHolder) view.getTag();
            }

            countryViewHolder.getNames().setText(countryItems.get(i).getCityName());
            String flagimg = countryItems.get(i).getFlag();
            if (!StringEmptyUtil.isEmptyString(flagimg)) {
                countryViewHolder.getFlag().setVisibility(View.VISIBLE);
                Glide.with(Objects.requireNonNull(context))
                        .load(flagimg)
                        .into(countryViewHolder.getFlag());
            } else {
                countryViewHolder.getFlag().setVisibility(View.GONE);
            }
            return view;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


}
