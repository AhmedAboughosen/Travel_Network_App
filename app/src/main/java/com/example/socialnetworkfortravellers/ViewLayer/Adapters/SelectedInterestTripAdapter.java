package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestTripModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.InterestTripViewHolder;

import java.util.List;

public class SelectedInterestTripAdapter extends RecyclerView.Adapter<InterestTripViewHolder> {


    private List<String> interestModelArrayList;


    public SelectedInterestTripAdapter(List<String> interestModelArrayList) {
        this.interestModelArrayList = interestModelArrayList;
    }


    @NonNull
    @Override
    public InterestTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create item for recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest_trip, parent, false);
        return new InterestTripViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestTripViewHolder holder, int position) {
        holder.mInterestButton.setText(this.interestModelArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return interestModelArrayList.size();
    }
}
