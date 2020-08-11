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
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.TravellersNearbyViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringUtil;

import java.util.List;
import java.util.Objects;

public class TravellersNearbyAdapter extends RecyclerView.Adapter<TravellersNearbyViewHolder> {

    private List<UserModel> listOfUserModels;
    private Context context;

    public TravellersNearbyAdapter(List<UserModel> listOfUserModels, Context context) {
        this.listOfUserModels = listOfUserModels;
        this.context = context;

    }

    @NonNull
    @Override
    public TravellersNearbyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_travellers_nearby, parent, false);
        return new TravellersNearbyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TravellersNearbyViewHolder holder, int position) {
        if (!listOfUserModels.get(position).getUserInfoModel().getKeyOfUser().equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
            if (StringUtil.isEmptyString(listOfUserModels.get(position).getProfilePicture())) {
                Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(R.drawable.user_image).into(holder.mProfileImage);
            } else {
                Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(listOfUserModels.get(position).getProfilePicture()).into(holder.mProfileImage);
            }

            Glide.with(Objects.requireNonNull(Objects.requireNonNull(context))).load(listOfUserModels.get(position).getUserInfoModel().getCountryFlag()).into(holder.mCountryFlag);
            holder.mFullNameTextView.setText(listOfUserModels.get(position).getFullName());
            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, FriendProfileActivity.class);
                intent.putExtra("FriendKey", listOfUserModels.get(position).getUserInfoModel().getKeyOfUser());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);
            });
        }

    }

    @Override
    public int getItemCount() {
        return listOfUserModels.size();
    }
}
