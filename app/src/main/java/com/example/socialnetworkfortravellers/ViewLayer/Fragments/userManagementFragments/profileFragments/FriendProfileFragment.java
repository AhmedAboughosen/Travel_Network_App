package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.profileFragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TabModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters.IFriendProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendProfilePresenters.IFriendProfilePresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.FriendProfileInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.ChatMessageActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.ProfileFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripsFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICheckNetworkConnectivity;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.FollowStyleButtonUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.USER_KEY;


public class FriendProfileFragment extends ProfileFragment {

    public static final String FRIEND_KEY = "FriendKey";
    @BindView(R.id.follow_button)
    Button follow_button;
    @BindView(R.id.message)
    Button message;


    private String FriendKey;

    @Inject
    IFriendProfilePresenter mFriendProfilePresenter;
    @Inject
    UserSharedPrefManager mUserSharedPrefManager;

    private boolean isGetFollowersData, isUpdateFollowAndUnFollowData = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_profile, container, false);

        mCheckNetworkConnectivity = (ICheckNetworkConnectivity) getActivity();
        getBundleData();
        setUpInject();
        setUpViews(view);
        getUserData(FriendKey);
        setUpFriendProfilePresenterCallBack();
        checkIfUserFollowFriendOrNot();
        return view;
    }


    private void setUpInject() {
        FriendProfileInjector.getSharedInjector().injectIn(this);
    }

    /**
     * get key of current user.
     */
    private void getBundleData() {
        if (getArguments() != null) {
            FriendKey = getArguments().getString(FRIEND_KEY);
        }
    }


    private void setUpFriendProfilePresenterCallBack() {
        mFriendProfilePresenter.setUpFriendProfilePresenterCallBack(new IFriendProfilePresenterCallBack() {
            @Override
            public void isOneOfFollowers(boolean isFollower) {
                isGetFollowersData = true;
                follow_button.setEnabled(true);
                if (isFollower) {
                    FollowStyleButtonUtil.getInstance().changeStyleButton_follow(follow_button, getActivity());
                } else {
                    FollowStyleButtonUtil.getInstance().changeStyleButton_UnFollow(follow_button, getActivity());
                }
            }

            @Override
            public void showMessageError(@NonNull String databaseError) {
                BaseProgressDialog.getInstance().showMessagesError(databaseError, getActivity());
            }

            @Override
            public void networkErrorMessage() {
                BaseProgressDialog.getInstance().showMessagesError("there is No Internet.", getActivity());
            }

            @Override
            public void updateFollowersSuccessful() {
                isUpdateFollowAndUnFollowData = true;

                FollowStyleButtonUtil.getInstance().changeStyleButton_follow(follow_button, getActivity());
                // long update_number_of_followers = (Long.parseLong(number_of_Followers.getText().toString()) + 1);
                // number_of_Followers.setText(update_number_of_followers + "");
            }


            @Override
            public void updateFollowingSuccessful() {
                Log.e("updateFollowing", "updateUnFollowersSuccessful");
            }

            @Override
            public void updateUnFollowersSuccessful() {
                isUpdateFollowAndUnFollowData = true;
                FollowStyleButtonUtil.getInstance().changeStyleButton_UnFollow(follow_button, getActivity());
//                 long update_number_of_followers = (Long.parseLong(number_of_Followers.getText().toString()) - 1);
//                    number_of_Followers.setText(update_number_of_followers + "");
//                  FollowStyleButtonUtil.getInstance().changeStyleButton_UnFollow(follow_button, getActivity());
            }

            @Override
            public void updateUnFollowingSuccessful() {
                Log.e("updateUnFollowing", "updateUnFollowingSuccessful");
            }

            @Override
            public void updateNotificationSuccessful() {
                Log.e("updateNotification", "updateNotificationSuccessful");

            }
        });
    }


    private void checkIfUserFollowFriendOrNot() {
        mFriendProfilePresenter.getStateOfUser(FriendKey);
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
        bundle.putString(USER_KEY, FriendKey);

        PostsFragment postsFragment = new PostsFragment();
        postsFragment.setArguments(bundle);
        TripsFragment tripsFragment = new TripsFragment();
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


    /**
     * this method exe when user click on follow_button
     */
    @OnClick(R.id.follow_button)
    public void onFollowButtonClick() {


        if (isGetFollowersData && isUpdateFollowAndUnFollowData) {
            isUpdateFollowAndUnFollowData = false;
            if (follow_button.getText().toString().equals("Follow")) {

                mFriendProfilePresenter.updateFollowers();
                mFriendProfilePresenter.updateFollowing();
                NotificationsModel notificationsModel = new NotificationsModel();
                notificationsModel.setProfileImage(mUserSharedPrefManager.getImageUrl());
                notificationsModel.setFullName(mUserSharedPrefManager.getFullName());
                notificationsModel.setFriendKey(FriendKey);
                notificationsModel.setUserKey(CurrentUserIDUtil.getInstance().getCurrentUserID());
                mFriendProfilePresenter.updateNotificationOfUser(notificationsModel);
            } else {
                AlertUnFollowDialog();
            }
        }
    }


    private void AlertUnFollowDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        builder.setMessage("Stop Following " + user_name.getText().toString() + "?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    try {
                        dialog.cancel();
                        unFollow();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }


                })
                .setNegativeButton("No", (dialog, id) -> {
                    //  Action for 'NO' Button
                    isUpdateFollowAndUnFollowData = true;
                    dialog.cancel();
                });


        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("UnFollow");
        alert.show();
    }


    private void unFollow() {
        mFriendProfilePresenter.updateUnFollowers();
        mFriendProfilePresenter.updateUnFollowing();

        NotificationsModel notificationsModel = new NotificationsModel();
        notificationsModel.setProfileImage("");
        notificationsModel.setFullName("");
        notificationsModel.setFriendKey(FriendKey);
        notificationsModel.setUserKey(CurrentUserIDUtil.getInstance().getCurrentUserID());
        mFriendProfilePresenter.updateNotificationOfUser(notificationsModel);
    }

    @Override
    protected void loadUserSuccessfully() {

    }

    @OnClick(R.id.message)
    public void onStartMessageClick() {

        Intent intent = new Intent(getActivity(), ChatMessageActivity.class);
        intent.putExtra(ConfigUtil.FRIEND_KEY, FriendKey);
        startActivity(intent);
        animateWithZoom();
    }
}
