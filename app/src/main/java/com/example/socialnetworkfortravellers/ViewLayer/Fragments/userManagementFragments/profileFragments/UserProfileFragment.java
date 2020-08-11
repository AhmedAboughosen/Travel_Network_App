package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.profileFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TabModel;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.ProfileInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity.EditUserInformationActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.ProfileFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripsFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICheckNetworkConnectivity;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.USER_KEY;


/**
 * UserProfileFragment
 * <p>
 * created by Ahmed Naser ali
 */
public class UserProfileFragment extends ProfileFragment {


    public @BindView(R.id.edit_profile)
    ImageButton mEditUserImageButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);


        mCheckNetworkConnectivity = (ICheckNetworkConnectivity) getActivity();


        setUpInject();
        setUpViews(view);
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        getUserData(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }

    public void setUpInject() {
        ProfileInjector.getSharedInjector().injectIn(this);
    }

    @OnClick(R.id.edit_profile)
    public void onEditProfileClick() {
        Intent intent = new Intent(getActivity(), EditUserInformationActivity.class);
        intent.putExtra(EditUserInformationActivity.USER_OBJECT, mUserModel);

        startActivity(intent);
        animateWithZoom();
    }


    /**
     * set up views.
     */
    private void setUpViews(View views) {
        ButterKnife.bind(this, views);
        user_profile_shimmer.startShimmer();
        user_profile_Image_shimmer.startShimmer();

          /*
        list of viewPager
         */
        List<Fragment> mfragment = new ArrayList<>();
        Bundle bundle = new Bundle();
        bundle.putString(USER_KEY, CurrentUserIDUtil.getInstance().getCurrentUserID());

        PostsFragment postsFragment = new PostsFragment();
        TripsFragment tripsFragment = new TripsFragment();
        postsFragment.setArguments(bundle);
        tripsFragment.setArguments(bundle);
        mfragment.add(postsFragment);
        mfragment.add(tripsFragment);
        super.initViewPager(mfragment);

          /*
        list of tabs
         */
        List<TabModel> tabModels = new ArrayList<>();
        tabModels.add(new TabModel("Posts", 0));
        tabModels.add(new TabModel("Trips", 1));
        super.intiTabLayout(tabModels);

    }

    @Override
    protected void loadUserSuccessfully() {
        mEditUserImageButton.setVisibility(View.VISIBLE);
    }
}
