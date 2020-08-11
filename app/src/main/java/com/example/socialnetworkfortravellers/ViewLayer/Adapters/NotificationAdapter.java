package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.NotificationViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.INotificationAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.SetContentPostUtil;

import java.util.ArrayList;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationViewHolder> {

    private List<NotificationsModel> notificationsModelList;
    private FragmentActivity mContext;
    private INotificationAdapterCallback mNotificationAdapterCallback;


    public NotificationAdapter(FragmentActivity context) {
        notificationsModelList = new ArrayList<>();
        mContext = context;
    }


    public void updateNotifications(NotificationsModel notificationsModel) {
        notificationsModelList.add(notificationsModel);
        notifyDataSetChanged();
    }


    public void setUpNotificationAdapterCallback(INotificationAdapterCallback notificationAdapterCallback) {
        mNotificationAdapterCallback = notificationAdapterCallback;
    }


    public void removeNullItem() {
        if (notificationsModelList.get(0) == null) {
            notificationsModelList.remove(null);
        }
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            //create item for recycler view
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_notification, parent, false);
            return new NotificationViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationViewHolder holder, int position) {
        try {

            if (notificationsModelList.get(position) != null) {

                holder.mShimmerFrameLayout.stopShimmer();
                holder.mShimmerFrameLayout.setShimmer(null);

                holder.profile_picture.setImageResource(0);
                holder.Joined_date.setBackground(null);
                holder.profile_picture.setBackground(null);
                holder.mMessageTextView.setBackground(null);


                String sourceString = "<b>" + notificationsModelList.get(position).getFullName() + "</b> " + "started Following you.";

                holder.mMessageTextView.setText(Html.fromHtml(sourceString));

                SetContentPostUtil.setDate(holder.Joined_date, notificationsModelList.get(position).getDate().toString());

                GlideUtil.loadImage(mContext, notificationsModelList.get(position).getProfileImage(), holder.profile_picture);
                holder.itemView.setOnClickListener(view -> {
                    mNotificationAdapterCallback.onFriendClick(notificationsModelList.get(position).getFriendKey());
                });
            } else {
                holder.mShimmerFrameLayout.startShimmer();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return notificationsModelList.size();
    }


}
