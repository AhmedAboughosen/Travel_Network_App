package com.example.socialnetworkfortravellers.ViewLayer.Fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.utilLayer.NetworkState;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoInternetFragment extends Fragment {

    public @BindView(R.id.NoInternetButton)
    Button check_internet;

    @BindView(R.id.nowifi)
    LottieAnimationView nowifi;

    private Fragment mPrimaryFragment;
    private int mLayout;
    private FragmentManager mFragmentManager;

    public NoInternetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_no_internet, container, false);
        ButterKnife.bind(this, view);
        nowifi.setAnimation(R.raw.no_internet);
        return view;
    }


    @OnClick(R.id.NoInternetButton)
    public void checkInternet(View view) {
        if (NetworkState.isNetworkConnected(getActivity())) {
            UpdateFragmentUtil.loadFragment(this.mPrimaryFragment, mLayout, mFragmentManager);
        }
    }


    public void setPrimaryFragment(Fragment primaryFragment) {
        this.mPrimaryFragment = primaryFragment;
    }

    public void setLayout(int layout) {
        this.mLayout = layout;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

}
