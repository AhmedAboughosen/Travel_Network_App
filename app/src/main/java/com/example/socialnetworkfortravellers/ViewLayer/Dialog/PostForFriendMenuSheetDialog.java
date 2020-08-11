package com.example.socialnetworkfortravellers.ViewLayer.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;
import com.example.socialnetworkfortravellers.nodesLayer.HidePostEvent;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PostForFriendMenuSheetDialog extends BottomSheetDialogFragment {


    private int index;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.postfriendmenusheetdialog, container, false);
        ButterKnife.bind(this, view);
        getData();
        return view;
    }


    private void getData() {
        if (getArguments() != null) {
            index = getArguments().getInt(PostsFragment.POST_INDEX);
            setArguments(null);
        }
    }

    @OnClick(R.id.hide_post)
    public void savePost() {
        EventBus.getDefault().postSticky(new HidePostEvent(index));
        dismiss();
    }

}
