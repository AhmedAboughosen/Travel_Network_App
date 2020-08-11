package com.example.socialnetworkfortravellers.ViewLayer.Adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.ICountryListAdapterCallback;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.CountryListViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CountryListAdapter extends RecyclerView.Adapter<CountryListViewHolder> {


    private List<CountryModel> countryItemList;
    private Context context;
    private ICountryListAdapterCallback mCountryListAdapterCallback;


    @Inject
    public CountryListAdapter(Context context) {
        this.countryItemList = new ArrayList<>();
        this.context = context;
    }


    public void updateCountries(List<CountryModel> countryItemList) {
        this.countryItemList = new ArrayList<>(countryItemList);
        notifyDataSetChanged();
    }

    /**
     * on Create View Holder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public CountryListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_country_list, parent, false);
            return new CountryListViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull CountryListViewHolder holder, int position) {

        try {
            //set Country Name.
            holder.country_name.setText(this.countryItemList.get(position).getCityName());

            //set Profile Picture.
            if (this.countryItemList.get(position).getFlag() != null) {
                GlideUtil.loadImage(context, this.countryItemList.get(position).getFlag(), holder.country_flag);
            }

            holder.itemView.setOnClickListener(view -> mCountryListAdapterCallback.onCountryClick(this.countryItemList.get(position).getCityName()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return this.countryItemList.size();
    }

    public void setUpCountryListAdapterCallback(ICountryListAdapterCallback mCountryListAdapterCallback) {
        this.mCountryListAdapterCallback = mCountryListAdapterCallback;
    }
}

