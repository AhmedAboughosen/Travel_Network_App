package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.IActiveUserCallback;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.ActiveUserViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class ActiveUserAdapter extends RecyclerView.Adapter<ActiveUserViewHolder> {

    private List<UserModel> mListOfUsers;
    private Context mContext;
    private IActiveUserCallback mActiveUserCallback;

    @Inject
    public ActiveUserAdapter(Context context) {
        this.mContext = context;
        mListOfUsers = new ArrayList<>();
    }


    /**
     * update items
     *
     * @param list
     */
    public void updateItems(List<UserModel> list) {
        mListOfUsers.clear();
        mListOfUsers.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ActiveUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_active_user_layout, parent, false);
        return new ActiveUserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ActiveUserViewHolder holder, int position) {


        if (this.mListOfUsers.get(position) != null) {

            holder.shimmer.stopShimmer();
            holder.shimmer.setShimmer(null);
            holder.fullname.setBackground(null);
            holder.bio.setBackground(null);
            holder.profile_image.setImageResource(0);


            //set Full Name of User
            holder.fullname.setText(this.mListOfUsers.get(position).getFullName());

            //set mBio Of User
            holder.bio.setText(this.mListOfUsers.get(position).getBio());


            //set Profile Picture of user
            if (!StringEmptyUtil.isEmptyString(this.mListOfUsers.get(position).getProfilePicture())) {
                GlideUtil.loadImage(mContext, this.mListOfUsers.get(position).getProfilePicture(), holder.profile_image);
            } else {
                holder.profile_image.setImageResource(R.drawable.user_image);
            }

            holder.itemView.setOnClickListener(v -> mActiveUserCallback.startFriendProfileActivity(mListOfUsers.get(position).getUserInfoModel().getKeyOfUser()));
        } else {
            holder.shimmer.startShimmer();
        }
    }

    @Override
    public int getItemCount() {
        return mListOfUsers.size();
    }

    public void setUpActiveUserCallback(IActiveUserCallback activeUserCallback) {
        this.mActiveUserCallback = activeUserCallback;
    }
}
