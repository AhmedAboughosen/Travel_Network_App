package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.SelectedImageViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IRemoveImageItem;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;

import java.util.ArrayList;

import javax.inject.Inject;

public class SelectedImageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context mContext;
    private ArrayList<String> mImageList;
    private IRemoveImageItem mRemoveImageItem;

    @Inject
    public SelectedImageAdapter(Context context) {
        mContext = context;
        mImageList = new ArrayList<>();
    }

    public void updateItems(ArrayList<String> imageList) {
        this.mImageList = new ArrayList<>(imageList);
    }

    public ArrayList<String> getImages() {
        return mImageList;
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
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_selected_image_post, parent, false);
            return new SelectedImageViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

          /*
        casting
         */
        SelectedImageViewHolder imagePath = (SelectedImageViewHolder) holder;

        try {
            /*
            bind data
             */
            if (mImageList != null && mImageList.get(position) != null) {
                //Bind Image based on image path
                GlideUtil.loadImage(mContext, mImageList.get(position), imagePath.set_image);
            }

            //delete Image Click
            imagePath.delete_image.setOnClickListener(view -> mRemoveImageItem.removeImage(position));

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    public void setUpRemoveImageItem(IRemoveImageItem mRemoveImageItem) {
        this.mRemoveImageItem = mRemoveImageItem;
    }
}

