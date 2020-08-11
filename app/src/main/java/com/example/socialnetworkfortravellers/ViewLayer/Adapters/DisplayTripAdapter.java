package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.DisplayTripViewHolders;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOnTripItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class DisplayTripAdapter extends RecyclerView.Adapter<DisplayTripViewHolders> {


    private List<TripModel> tripModelList;
    private IOnTripItem mOnTripItem;

    @Inject
    public DisplayTripAdapter() {
        tripModelList = new ArrayList<>();
    }

    public void setIOnTripItem(IOnTripItem onTripItem) {
        this.mOnTripItem = onTripItem;
    }


    public void updateDate(List<TripModel> list) {
        tripModelList.addAll(list);
        notifyDataSetChanged();
    }


    public void removeAt(int index) {
        tripModelList.remove(index);
        notifyItemRemoved(index);
    }

    @NonNull
    @Override
    public DisplayTripViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create item for recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trip_layout, parent, false);
        return new DisplayTripViewHolders(v);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayTripViewHolders holder, int position) {

        TripModel tripModel = tripModelList.get(position);
        holder.mCountryName.setText(tripModel.getCountryName());
        holder.mDateTime.setText("from " + tripModel.getFrom() + " to " + tripModel.getTo());
        int selectImage = (randomImageWithRange(1, 10) <= 5 ? R.drawable.trip_country_image_1 : R.drawable.trip_country_image_2);
        holder.mCountryImage.setBackgroundResource(selectImage);

        holder.itemView.setOnClickListener(v -> mOnTripItem.onTripClick(tripModel, selectImage));
        holder.itemView.setOnLongClickListener(v -> {
            mOnTripItem.onDeleteTrip(tripModel.getTripKey(), position);
            return false;
        });
    }

    private int randomImageWithRange(int min, int max) {
        int range = (max - min) + 1;
        return (int) (Math.random() * range) + min;
    }

    @Override
    public int getItemCount() {
        return tripModelList.size();
    }
}
