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
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.FriendSuggestionViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFriendSuggestionAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FriendSuggestionAdapter extends RecyclerView.Adapter<FriendSuggestionViewHolder> {

    private ArrayList<UserModel> mListOfUsers;
    private FragmentActivity mFragmentActivity;
    private RecyclerView mRecyclerView;
    private IFriendSuggestionAdapterCallback friendSuggestionAdapterCallback;


    public FriendSuggestionAdapter(FragmentActivity context) {
        this.mListOfUsers = new ArrayList<>();
        this.mFragmentActivity = context;
    }


    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


    public void setUpFriendSuggestionAdapterCallback(IFriendSuggestionAdapterCallback friendSuggestionAdapterCallback) {
        this.friendSuggestionAdapterCallback = friendSuggestionAdapterCallback;
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
     * update list
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


    @NonNull
    @Override
    public FriendSuggestionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create item for recycler view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend_suggested, parent, false);
        return new FriendSuggestionViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull FriendSuggestionViewHolder holder, int position) {
        try {
            /*
             * set main data such as bio and full name and Joined data profile picture
             */

            if (mListOfUsers.get(position) != null) {

                holder.shimmer.stopShimmer();
                holder.shimmer.setShimmer(null);
                holder.mBio.setBackground(null);
                holder.full_name.setBackground(null);
                holder.profile_picture.setImageResource(0);
                holder.mNumberOfFollowers.setBackground(null);
                holder.profile_picture.setBackground(null);
                setData(holder, position);


                holder.card_view.setOnClickListener(view -> {
                    friendSuggestionAdapterCallback.onFriendClick(mListOfUsers.get(position).getUserInfoModel().getKeyOfUser());
                });
            } else {
                holder.shimmer.startShimmer();
            }


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public void setData(FriendSuggestionViewHolder holder, int position) {

              /*
                set bio data
                 */
        if (!StringEmptyUtil.isEmptyString(mListOfUsers.get(position).getBio())) {
            holder.mBio.setVisibility(View.VISIBLE);
            holder.mBio.setText(mListOfUsers.get(position).getBio());
        } else {
            holder.mBio.setVisibility(View.INVISIBLE);
        }

              /*
            set name of user
             */

        holder.full_name.setText(mListOfUsers.get(position).getFullName());


        holder.mNumberOfFollowers.setText(mListOfUsers.get(position).getUserInfoModel().getNumber_Of_Followers() + " Followers .");

                /*
                set Profile Image
                 */
        if (!StringEmptyUtil.isEmptyString(mListOfUsers.get(position).getProfilePicture())) {
            Glide.with(Objects.requireNonNull(Objects.requireNonNull(mFragmentActivity))).load(mListOfUsers.get(position).getProfilePicture()).into(holder.profile_picture);
        } else {
            holder.profile_picture.setImageResource(R.drawable.user_image);
        }

    }

    @Override
    public int getItemCount() {
        return this.mListOfUsers.size();
    }
}
