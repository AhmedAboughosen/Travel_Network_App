package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestTripModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.InterestTripViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InterestTripAdapter extends RecyclerView.Adapter<InterestTripViewHolder> {

    private List<InterestTripModel> interestModelArrayList;
    private Context mContext;


    @Inject
    public InterestTripAdapter(Context context) {
        interestModelArrayList = new ArrayList<>();
        mContext = context;
        initData();
    }


    public List<InterestTripModel> getInterests() {
        return interestModelArrayList;
    }

    @NonNull
    @Override
    public InterestTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_interest_trip, parent, false);
            return new InterestTripViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestTripViewHolder holder, int position) {
        try {
            //set interest name of User
            holder.mInterestButton.setText(this.interestModelArrayList.get(position).getInterestName());

            /*
            check if object is Mark Or not
             */
            if (interestModelArrayList.get(position).isMarked()) {
                holder.mInterestButton.setBackgroundResource(R.drawable.custom_button_for_interest_trip_marked);
                holder.mInterestButton.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                holder.mInterestButton.setBackgroundResource(R.drawable.custom_button_for_interest_trip);
                holder.mInterestButton.setTextColor(mContext.getResources().getColor(R.color.black));
            }


            /*
            when user click on View
             */
            holder.mInterestButton.setOnClickListener(view -> {
                InterestTripModel interestModel = interestModelArrayList.get(position);

                if (interestModelArrayList.get(position).isMarked()) {
                    interestModel.setMarked(false);
                    holder.mInterestButton.setBackgroundResource(R.drawable.custom_button_for_interest_trip);
                    holder.mInterestButton.setTextColor(mContext.getResources().getColor(R.color.black));
                } else {
                    interestModel.setMarked(true);
                    holder.mInterestButton.setBackgroundResource(R.drawable.custom_button_for_interest_trip_marked);
                    holder.mInterestButton.setTextColor(mContext.getResources().getColor(R.color.white));
                }

            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return interestModelArrayList.size();
    }


    /**
     * init data of Recycler view
     */
    public void initData() {
        addData("Beach");
        addData("Nature");
        addData("Culture");
        addData("Adventure");
        addData("Party");
        addData("Sport");
        addData("Shopping");
        addData("Foodies");
    }


    /**
     * add Data
     *
     * @param Name
     */
    private void addData(String Name) {
        try {
            InterestTripModel interestModel = new InterestTripModel();
            interestModel.setInterestName(Name);
            interestModelArrayList.add(interestModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
