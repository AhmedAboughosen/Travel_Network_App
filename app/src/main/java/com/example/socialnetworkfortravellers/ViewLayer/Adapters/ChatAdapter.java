package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.MineChatViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.OtherChatViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private final int VIEW_TYPE_ME = 1;
    private final int VIEW_TYPE_OTHER = 2;
    private List<MessageModel> messageModelList;
    private String profileImageUrl;
    private Context mContext;

    public ChatAdapter(Context context) {
        messageModelList = new ArrayList<>();
        this.mContext = context;
    }


    @Override
    public int getItemViewType(int position) {
        if (messageModelList.get(position).getUserKey().equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
            return VIEW_TYPE_ME;
        } else {
            return VIEW_TYPE_OTHER;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        RecyclerView.ViewHolder viewHolder = null;
        switch (viewType) {
            case VIEW_TYPE_ME:
                View viewChatMine = layoutInflater.inflate(R.layout.item_chat_mine, parent, false);
                viewHolder = new MineChatViewHolder(viewChatMine);
                break;
            case VIEW_TYPE_OTHER:
                View viewChatOther = layoutInflater.inflate(R.layout.item_chat_other, parent, false);
                viewHolder = new OtherChatViewHolder(viewChatOther);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MineChatViewHolder) {
            MineChatViewHolder mineChatViewHolder = (MineChatViewHolder) holder;
            mineChatViewHolder.txtChatMessage.setText(messageModelList.get(position).getMessageContent());
            mineChatViewHolder.txtChatMessageTime.setText(ConvertTimeUtil.toTimeStamp(messageModelList.get(position).getTimestamp().toString()));
        } else {
            OtherChatViewHolder otherChatViewHolder = (OtherChatViewHolder) holder;
            otherChatViewHolder.txtChatMessage.setText(messageModelList.get(position).getMessageContent());
            otherChatViewHolder.txtChatMessageTime.setText(ConvertTimeUtil.toTimeStamp(messageModelList.get(position).getTimestamp().toString()));
            if (!StringEmptyUtil.isEmptyString(profileImageUrl)) {
                GlideUtil.loadImage(mContext, profileImageUrl, otherChatViewHolder.mProfileCircleImageView);
            } else {
                GlideUtil.loadImage(mContext, R.drawable.user_image, otherChatViewHolder.mProfileCircleImageView);
            }
        }

    }


    public void onNewMessageAdded(MessageModel messageModel) {

        messageModelList.add(messageModel);
        this.notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }


}
