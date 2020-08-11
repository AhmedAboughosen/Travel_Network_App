package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.MessengerViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessengerAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.List;

public class MessengerAdapter extends RecyclerView.Adapter<MessengerViewHolder> implements Filterable {

    private Context mContext;
    private List<MessageModel> mListMessageModel;
    private List<MessageModel> mListMessageModelForSearch;
    private IMessengerAdapterCallback mMessengerAdapterCallback;

    public MessengerAdapter(Context context, List<MessageModel> messageModelList) {
        this.mListMessageModel = new ArrayList<>(messageModelList);
        this.mContext = context;
        mListMessageModelForSearch = new ArrayList<>(messageModelList);
    }

    public void setUpIMessengerAdapterCallback(IMessengerAdapterCallback mMessengerAdapterCallback) {
        this.mMessengerAdapterCallback = mMessengerAdapterCallback;
    }

    @NonNull
    @Override
    public MessengerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messenger_friend, parent, false);
        return new MessengerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MessengerViewHolder holder, int position) {

        if (!StringEmptyUtil.isEmptyString(mListMessageModel.get(position).getUserModel().getProfilePicture())) {
            GlideUtil.loadImage(mContext, mListMessageModel.get(position).getUserModel().getProfilePicture(), holder.mProfileImageCircleImageView);
        } else {
            GlideUtil.loadImage(mContext, R.drawable.user_image, holder.mProfileImageCircleImageView);
        }

        holder.mFullNameTextView.setText(mListMessageModel.get(position).getUserModel().getFullName());
        holder.mLastMessageTextView.setText(mListMessageModel.get(position).getMessageContent());
        if (mListMessageModel.get(position).getTimestamp() != null)
            holder.mTextViewTime.setText(". " + ConvertTimeUtil.toTimeStamp(mListMessageModel.get(position).getTimestamp().toString()));

        holder.itemView.setOnClickListener(v -> {
            mMessengerAdapterCallback.onChatClick(mListMessageModel.get(position).getUserModel().getUserInfoModel().getKeyOfUser());
        });

    }

    @Override
    public int getItemCount() {
        return mListMessageModel.size();
    }

    @Override
    public Filter getFilter() {
        return messengerFilter;
    }

    private Filter messengerFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults results = new FilterResults();

            try {

                results.values = Filter(constraint);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            try {
                mListMessageModel.clear();
                mListMessageModel.addAll((List) results.values);
                if (mListMessageModel.size() == 0) {
                    Toast.makeText(mContext, "no Friend exists", Toast.LENGTH_LONG).show();
                    return;
                }
                notifyDataSetChanged();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
    };

    public List<MessageModel> Filter(CharSequence constraint) {
        List<MessageModel> filteredList = new ArrayList<>();

        try {
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(mListMessageModelForSearch);
            } else {
                String filterPattern = constraint.toString();

                for (MessageModel item : mListMessageModelForSearch) {
                    String name = item.getUserModel().getFullName();

                    if (name.contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return filteredList;
    }
}
