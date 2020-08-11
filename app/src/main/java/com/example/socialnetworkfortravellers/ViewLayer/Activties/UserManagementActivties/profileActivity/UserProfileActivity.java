package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.AddPostActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.AddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.NoInternetFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.profileFragments.UserProfileFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICheckNetworkConnectivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

/**
 * UserProfileActivity
 * <p>
 * created by Ahmed Naser ali
 */

public class UserProfileActivity extends BaseActivity implements ICheckNetworkConnectivity, IMessageFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        LoadUserProfileFragment();
    }


    /**
     * load  User Profile Fragment.
     */
    public void LoadUserProfileFragment() {
        UpdateFragmentUtil.loadFragment(new UserProfileFragment(), getSupportFragmentManager(), R.id.load_fragment);
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
        noInternetFragment.setPrimaryFragment(new UserProfileFragment());
        noInternetFragment.setFragmentManager(getSupportFragmentManager());

        UpdateFragmentUtil.loadFragment(noInternetFragment, getSupportFragmentManager(), R.id.load_fragment);
    }


    @Override
    public void onButtonClick(String str) {
        if (str.toLowerCase().contains("post")) {
            startActivity(new Intent(getApplicationContext(), AddPostActivity.class));
        } else {
            startActivity(new Intent(getApplicationContext(), AddTripActivity.class));
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
