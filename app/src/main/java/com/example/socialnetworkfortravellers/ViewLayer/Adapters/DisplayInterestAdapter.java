package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.DisplayInterestViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

public class DisplayInterestAdapter extends RecyclerView.Adapter<DisplayInterestViewHolder> {

    private List<String> interestModelArrayList;
    private Context mContext;
    private HashMap<String, Integer> interestMap;


    @Inject
    public DisplayInterestAdapter(Context context) {
        interestModelArrayList = new ArrayList<>();
        interestMap = new HashMap<>();
        mContext = context;
        initData();
    }

    public List<String> getInterests() {
        return interestModelArrayList;
    }


    @NonNull
    @Override
    public DisplayInterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_display_interest, parent, false);
            return new DisplayInterestViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayInterestViewHolder holder, int position) {
        try {
            holder.interest_name.setText(this.interestModelArrayList.get(position));
            Glide.with(mContext).load(interestMap.get(this.interestModelArrayList.get(position))).into(holder.interest_image);


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
        addData(R.drawable.adventure, "adventure");
        addData(R.drawable.photography, "photography");
        addData(R.drawable.travel_blogging, "Travel Blogging");
        addData(R.drawable.solotraveller, "Solo Travel");
        addData(R.drawable.backpacking, "Backpacking");
        addData(R.drawable.internationalstudy, "International Study");
        addData(R.drawable.digitalnomad, "Digital Nomad");
        addData(R.drawable.outdoors, "Outdoors");
        addData(R.drawable.vanlife, "Van Life");
        addData(R.drawable.meetinglocals, "Meeting Locals");
        addData(R.drawable.food, "Food && Cuisine");
        addData(R.drawable.archaeologicallandmarks, "Sightseeing");
        addData(R.drawable.skiing, "Skiing");
        addData(R.drawable.exercise, "Exercise");
        addData(R.drawable.surfing, "Surfing");
        addData(R.drawable.history, "History");
        addData(R.drawable.shopping, "Shopping");
        addData(R.drawable.sportevents, "Sport Events");

    }


    /**
     * add Data
     *
     * @param Image
     * @param Name
     */
    private void addData(int Image, String Name) {
        interestMap.put(Name, Image);
    }

}
