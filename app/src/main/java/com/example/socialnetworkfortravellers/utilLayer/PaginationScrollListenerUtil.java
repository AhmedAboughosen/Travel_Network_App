package com.example.socialnetworkfortravellers.utilLayer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.utilLayer.interfaces.IOnLoadMoreListener;

public class PaginationScrollListenerUtil implements RecyclerView.OnChildAttachStateChangeListener {

    @NonNull
    private IOnLoadMoreListener listener;
    private RecyclerView mRecyclerView;


    /**
     * Supporting only LinearLayoutManager for now.
     */
    public PaginationScrollListenerUtil(@NonNull RecyclerView mRecyclerView, IOnLoadMoreListener listener) {
        this.mRecyclerView = mRecyclerView;
        this.listener = listener;
    }


    @Override
    public void onChildViewAttachedToWindow(@NonNull View view) {
        try {
            RecyclerView.Adapter adapter = mRecyclerView.getAdapter();
            RecyclerView.LayoutManager mgr = mRecyclerView.getLayoutManager();

            int adapterPosition = mgr.getPosition(view);

            if (!listener.isLoading() && adapterPosition == adapter.getItemCount() - 1) {
                // last view was attached
                listener.loadMoreItems();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void onChildViewDetachedFromWindow(@NonNull View view) {

    }
}