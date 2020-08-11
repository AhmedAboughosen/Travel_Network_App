package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.suggestionActivity;

import android.os.Bundle;

import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.suggestionFragments.FriendSuggestionFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFriendSuggestionActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import butterknife.OnClick;

public class FriendSuggestionActivity extends BaseActivity implements IFriendSuggestionActivity, IMessageFragment {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_suggestion);

        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.FRIEND_SUGGESTION);

        initView();
        LoadFriendSuggestionFragment();
    }


    public void LoadFriendSuggestionFragment() {
        UpdateFragmentUtil.loadFragment(new FriendSuggestionFragment(), R.id.fragment_layout, getSupportFragmentManager());
    }

    @Override
    public void thereIsNoDataToProvide() {
        DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
        displayMessageFragment.setMessage("we can't find  friends which matches your interest or country.");
        displayMessageFragment.setLottieAnimation(R.raw.friendships);
        displayMessageFragment.setButtonTitle("find Friends");
        UpdateFragmentUtil.loadFragment(displayMessageFragment, R.id.fragment_layout, getSupportFragmentManager());
    }

    @OnClick(R.id.next)
    public void onNextButtonClick() {
        sendUserToMainActivity();
    }

    @Override
    public void onBackPressed() {
        sendUserToMainActivity();
    }


    private void sendUserToMainActivity() {
        SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(FriendSuggestionActivity.this, MainActivity.class);
        finish();
        finishAffinity();
    }

    @Override
    public void onButtonClick(String str) {
        SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(FriendSuggestionActivity.this, FindFriendActivity.class);
    }
}
