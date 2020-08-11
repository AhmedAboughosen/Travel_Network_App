package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.FoundFriendViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFoundFriendsAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FoundFriendsAdapter extends RecyclerView.Adapter<FoundFriendViewHolder> {

    private FragmentActivity mFragmentActivity;
    private ArrayList<UserModel> mListOfUsers;
    private RecyclerView mRecyclerView;
    private IFoundFriendsAdapterCallback mIFoundFriendsAdapterCallback;


    public FoundFriendsAdapter(FragmentActivity context) {
        this.mFragmentActivity = context;
        this.mListOfUsers = new ArrayList<>();
    }


    public void setUpFoundFriendsAdapterCallback(IFoundFriendsAdapterCallback foundFriendsAdapterCallback){
        this.mIFoundFriendsAdapterCallback = foundFriendsAdapterCallback;
    }


    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


    /**
     * add fake data while send Request to server.
     */
    public void addFakeData() {
        mRecyclerView.post(() -> mFragmentActivity.runOnUiThread(() -> {
            if (mListOfUsers.size() == 0 || (mListOfUsers.get(mListOfUsers.size() - 1) != null)) {
                mListOfUsers.add(null);
                notifyItemInserted(mListOfUsers.size() - 1);
            }
        }));

    }


    /**
     * this method called when we get response data from server, this mean remove progress bar
     */
    public void removeFakeData() {

        mRecyclerView.post(() -> mFragmentActivity.runOnUiThread(() -> {
            if (mListOfUsers.size() > 0 && mListOfUsers.get(mListOfUsers.size() - 1) == null) {
                mListOfUsers.remove(mListOfUsers.size() - 1);
                notifyItemRemoved(mListOfUsers.size());
            }
        }));

    }


    /**
     * add a users in list
     *
     * @param userModels
     */
    public void updateItems(List<UserModel> userModels) {

        mRecyclerView.post(() -> mFragmentActivity.runOnUiThread(() -> {
            int initSize = mListOfUsers.size();
            mListOfUsers.addAll(userModels);
            notifyItemRangeChanged(initSize, mListOfUsers.size());
        }));

    }


    public void removeAllItems() {
        mListOfUsers = new ArrayList<>();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public FoundFriendViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create item for recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_layout, parent, false);
        return new FoundFriendViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundFriendViewHolder holder, int position) {
        try {

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
                    Glide.with(Objects.requireNonNull(Objects.requireNonNull(mFragmentActivity))).load(this.mListOfUsers.get(position).getProfilePicture()).into(holder.profile_image);
                } else {
                    holder.profile_image.setImageResource(R.drawable.user_image);
                }

                holder.itemView.setOnClickListener(v -> {
                    mIFoundFriendsAdapterCallback.onChatClick(mListOfUsers.get(position).getUserInfoModel().getKeyOfUser());
                });
            } else {
                holder.shimmer.startShimmer();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.mListOfUsers.size();
    }
}
