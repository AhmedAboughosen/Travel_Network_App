package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NoFriendFoundFragment extends Fragment {

    private String name = "";
    @BindView(R.id.Name)
    TextView mNameTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_friend_found, container, false);
        ButterKnife.bind(this, view);
        setFriendName();
        return view;
    }


    private void setFriendName() {
        if (getArguments() != null) {
            name = getArguments().getString("FriendName");
        }

        mNameTextView.setText(name);
    }

}
