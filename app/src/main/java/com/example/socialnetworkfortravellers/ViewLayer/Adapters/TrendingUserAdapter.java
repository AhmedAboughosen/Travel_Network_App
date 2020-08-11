package com.example.socialnetworkfortravellers.ViewLayer.Adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.UserTrendingViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.FlexibleColorUtli;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TrendingUserAdapter extends RecyclerView.Adapter<UserTrendingViewHolder> {

    private Context context;
    private ArrayList<UserModel> users;

    public TrendingUserAdapter(Context context) {
        this.context = context;
        this.users = new ArrayList<>();
    }


    /**
     * update RecyclerView with a new User
     *
     * @param users
     */
    public void updateItems(List<UserModel> users) {
        this.users.addAll(users);
    }


    /**
     * onCreateViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public UserTrendingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trending_user, parent, false);
            return new UserTrendingViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * onBindViewHolder
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull UserTrendingViewHolder holder, int position) {

        //set Full Name of User
        holder.fullname.setText(this.users.get(position).getFullName());

        //set Profile Picture of user

        if (!StringEmptyUtil.isEmptyString(this.users.get(position).getProfilePicture())) {
            FlexibleColorUtli.setFlexible_color(this.users.get(position).getProfilePicture(), holder.flexible_color, context);
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(this.users.get(position).getProfilePicture()).into(holder.profile_image);
        }

        //set Full Name of User
        holder.mBioTextView.setText(this.users.get(position).getBio());
        holder.mLocationTextView.setText(users.get(position).getCountry());
        if (users.get(position).getUserInfoModel().getJoined_date() != null) {
            String current_Time = "Joined " + ConvertTimeUtil.fromTimestampToMonth(users.get(position).getUserInfoModel().getJoined_date().toString());
            holder.mJoinedDateTextView.setText(current_Time);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FriendProfileActivity.class);
            intent.putExtra("FriendKey", users.get(position).getUserInfoModel().getKeyOfUser());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }


}
