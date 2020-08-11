package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.InterestViewHolder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class InterestAdapter extends RecyclerView.Adapter<InterestViewHolder> {

    private List<InterestModel> interestModelArrayList;
    private Context mContext;


    @Inject
    public InterestAdapter(Context context) {
        interestModelArrayList = new ArrayList<>();
        mContext = context;
        initData();
    }


    public List<InterestModel> getInterests() {
        return interestModelArrayList;
    }


    public void updateInterest(List<InterestModel> interestModelArrayList) {
        this.interestModelArrayList = new ArrayList<>(interestModelArrayList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InterestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_interest, parent, false);
            return new InterestViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull InterestViewHolder holder, int position) {
        try {
            //set interest name of User
            holder.interest_name.setText(this.interestModelArrayList.get(position).getHobbyName());

            // holder.interest_name.setGravity(Gravity.CENTER);
            //load hobby Image into ImageView
            Glide.with(mContext).load(this.interestModelArrayList.get(position).getHobbyImage()).into(holder.interest_image);


            //set Image as marked
            holder.check_mark.setAnimation(R.raw.checked_done);

            /*
            check if object is Mark Or not
             */
            if (interestModelArrayList.get(position).isMarked()) {
                holder.check_mark.setVisibility(View.VISIBLE);
            } else {
                holder.check_mark.setVisibility(View.GONE);
            }


            /*
            when user click on View
             */
            holder.card_view.setOnClickListener(view -> {

                if (holder.check_mark.getVisibility() != View.VISIBLE) {
                    holder.check_mark.setVisibility(View.VISIBLE);
                    holder.check_mark.playAnimation();
                    InterestModel interestModel = interestModelArrayList.get(position);
                    interestModel.setMarked(true);
                } else {
                    holder.check_mark.setVisibility(View.GONE);
                    holder.check_mark.pauseAnimation();
                    InterestModel interestModel = interestModelArrayList.get(position);
                    interestModel.setMarked(false);
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
        try {
            InterestModel interestModel = new InterestModel();
            interestModel.setHobbyImage(Image);
            interestModel.setHobbyName(Name);
            interestModelArrayList.add(interestModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
