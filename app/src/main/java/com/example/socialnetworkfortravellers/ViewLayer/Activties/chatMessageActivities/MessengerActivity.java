package com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IMessengerPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IMessengerPresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.chatMessageInjectors.MessengerInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.MessengerAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnTextChanged;

public class MessengerActivity extends BaseActivity implements IMessageFragment {


    private MessengerAdapter mMessengerAdapter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    IMessengerPresenter mMessengerPresenter;
    @Inject
    UserSharedPrefManager sharedPrefManager;

    @BindView(R.id.RecyclerViewFriendList)
    RecyclerView mRecyclerViewFriendList;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        setUpInject();
        initView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN);

        setUpMessengerPresenterCallback();
        getMessageBranch();
    }


    private void setUpInject() {
        MessengerInjector.getSharedInjector().injectIn(this);
    }

    private void getMessageBranch() {
        mMessengerPresenter.getMessageBranch(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    @OnTextChanged(R.id.searchBar)
    public void onSearchTextChange(CharSequence text, int i, int x, int y) {
        if (mMessengerAdapter != null && mMessengerAdapter.getFilter() != null)
            mMessengerAdapter.getFilter().filter(text);
    }


    private void setUpMessengerPresenterCallback() {
        mMessengerPresenter.setUpMessengerPresenterCallback(new IMessengerPresenterCallback() {
            @Override
            public void currentUserDoesNotHaveFriends() {
                mProgressBar.setVisibility(View.GONE);
                DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
                displayMessageFragment.setMessage("you don't have friends yet");
                displayMessageFragment.setLottieAnimation(R.raw.friendships);
                displayMessageFragment.setButtonTitle("add new friend");
                UpdateFragmentUtil.loadFragment(displayMessageFragment, getSupportFragmentManager(), R.id.friendsList);
            }

            @Override
            public void listOfChatFriends(List<MessageModel> messageModels) {
                mProgressBar.setVisibility(View.GONE);
                mMessengerAdapter = new MessengerAdapter(getApplicationContext(), messageModels);
                InitializeRecyclerViewUtil.initVerticalRecyclerView(mRecyclerViewFriendList, getApplicationContext(), mMessengerAdapter);
                runLayoutAnimation(mRecyclerViewFriendList);
                mMessengerAdapter.setUpIMessengerAdapterCallback(key -> {
                    Intent intent = new Intent(getApplicationContext(), ChatMessageActivity.class);
                    intent.putExtra(ConfigUtil.FRIEND_KEY, key);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    animateWithZoom();
                });
            }

            @Override
            public void showMessageError(String message) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MessengerActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(MessengerActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });
    }

    protected void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }

    @Override
    public void onButtonClick(String str) {
        startActivity(new Intent(getApplicationContext(), FindFriendActivity.class));
        animateWithZoom();
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}