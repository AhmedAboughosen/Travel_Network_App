package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.AddPostActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.AddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.NoInternetFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.profileFragments.FriendProfileFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICheckNetworkConnectivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

public class FriendProfileActivity extends BaseActivity implements ICheckNetworkConnectivity, IMessageFragment {

    public static String activeFragment = "post";

    public static final String FRIEND_KEY = "FriendKey";
    private String mFriendKey;
    private FriendProfileFragment mFriendProfileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_profile);

        mFriendKey = getIntent().getStringExtra("FriendKey");

        LoadUserProfileFragment();
    }


    /**
     * load  User Profile Fragment.
     */
    public void LoadUserProfileFragment() {
        mFriendProfileFragment = new FriendProfileFragment();
        Bundle bundle = new Bundle();
        bundle.putString(FRIEND_KEY, mFriendKey);

        mFriendProfileFragment.setArguments(bundle);
        UpdateFragmentUtil.loadFragment(mFriendProfileFragment, R.id.load_fragment, getSupportFragmentManager());
    }


    /**
     * if there is no internet.
     */
    @Override
    public void noInternet() {
        LoadNoInternetFragment();
    }

    @Override
    public void exits() {
        finish();
        animateWithZoom();
    }


    /**
     * load no internet Fragment.
     */
    public void LoadNoInternetFragment() {
        NoInternetFragment noInternetFragment = new NoInternetFragment();
        noInternetFragment.setLayout(R.id.load_fragment);
        noInternetFragment.setPrimaryFragment(mFriendProfileFragment);
        noInternetFragment.setFragmentManager(getSupportFragmentManager());

        UpdateFragmentUtil.loadFragment(noInternetFragment, R.id.load_fragment, getSupportFragmentManager());
    }

    @Override
    public void onButtonClick(String str) {
     /*   if (activeFragment.equals("post")) {
            startActivity(new Intent(getApplicationContext(), AddPostActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), AddTripActivity.class));
        }*/
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
