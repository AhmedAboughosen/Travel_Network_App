package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IGetAllUserNamesPresenterCallBack;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IGetFullNameOfUsersPresenter;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.FindFriendInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.FriendsFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.MutualFriendFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.NoFriendFoundFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.BindView;

public class FindFriendActivity extends BaseActivity implements IFindFriendActivity, IMessageFragment {


    public static final String FULL_NAME = "FULL_NAME";
    private MutualFriendFragment mMutualFriendFragment;
    private FriendsFragment mFriendsFragment;
    private NoFriendFoundFragment mNoFriendFoundFragment;
    private DisplayMessageFragment mDisplayMessageFragment;
    private String mFullName = "";
    private boolean isActive = false;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.searchBar)
    EditText mSearchBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private Timer timer;

    @Inject
    public IGetFullNameOfUsersPresenter mGetAllUsersNamePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_friend);
        setUpInject();
        initView();
        searchEngine();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        setUpGetAllUsersNamePresenterCallBack();
    }


    private void setUpInject() {
        FindFriendInjector.getSharedInjector().injectIn(this);
    }


    private void setUpGetAllUsersNamePresenterCallBack() {

        mGetAllUsersNamePresenter.setUpGetAllUsersNamePresenterCallBack(new IGetAllUserNamesPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(FindFriendActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(FindFriendActivity.this, "there is no internet", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void allUsers(List<FoundUserModel> allUsers) {
                mProgressBar.setVisibility(View.GONE);
                isActive = true;
                setUpObjects();
                mFriendsFragment.setListOfUserName(allUsers);
            }

            @Override
            public void noUserExists() {
                mProgressBar.setVisibility(View.GONE);
                /*
                there is no user in database
                 */
                mDisplayMessageFragment = new DisplayMessageFragment();
                mDisplayMessageFragment.setMessage("server down --> there is no Friends exists in Database, please try later.");
                mDisplayMessageFragment.setLottieAnimation(R.raw.no_friends);
                updateFragment(mDisplayMessageFragment);
            }

        });


        mGetAllUsersNamePresenter.getAllFullNameOfUsers();
    }

    private void setUpObjects() {
        mMutualFriendFragment = new MutualFriendFragment();
        mNoFriendFoundFragment = new NoFriendFoundFragment();
        mFriendsFragment = new FriendsFragment();
        mFriendsFragment.setUpFriendActivity(this);
        updateFragment(mMutualFriendFragment);
    }


    /**
     * update fragment
     *
     * @param fragment
     */
    public void updateFragment(Fragment fragment) {
        UpdateFragmentUtil.loadFragment(fragment, R.id.container_layout, getSupportFragmentManager());
    }


    @Override
    public void noFriendExists() {
        Bundle bundle = new Bundle();
        bundle.putString("FriendName", mFullName);
        mNoFriendFoundFragment.setArguments(bundle);
        updateFragment(mNoFriendFoundFragment);
    }

    @Override
    public void internetIsNotConnected() {
        mDisplayMessageFragment = new DisplayMessageFragment();
        mDisplayMessageFragment.setMessage(StringConfigUtil.NO_INTEREST_USER_EXISTS);
        mDisplayMessageFragment.setLottieAnimation(R.raw.no_internet);
        updateFragment(mDisplayMessageFragment);
    }

    @Override
    public void currentUserDoesNotHaveFriends() {
        mDisplayMessageFragment = new DisplayMessageFragment();
        mDisplayMessageFragment.setMessage("you don't have any mutual friends.");
        mDisplayMessageFragment.setLottieAnimation(R.raw.sending_request);
        mDisplayMessageFragment.setEnabled(false);
        updateFragment(mDisplayMessageFragment);
    }


    /**
     * This snippet just shows you the exemplary use case to solve the problem on how to guess when the user finished typing and wants to start the search without having a submit button.
     * We use a delay of 600 milliseconds to start the search.
     * If the user types another letter into the search field, we reset the timer and wait another 600 ms.
     * learn more. https://futurestud.io/tutorials/android-how-to-delay-changedtextevent-on-androids-edittext
     */
    public void searchEngine() {
        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // user is typing: reset already started timer (if existing)
                if (timer != null) {
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // user typed: start the timer
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        // do your actual work here
                        runOnUiThread(() -> {

                            // Stuff that updates the UI
                            onTextChange(s.toString());
                        });
                    }
                }, 600); // 600ms delay before the timer executes the „run“ method from TimerTask
            }
        });
    }


    /**
     * check if test is null or empty display  Mutual Friend Fragment or FriendsFragment with texts.
     *
     * @param charSequence
     */
    public void onTextChange(CharSequence charSequence) {

        Log.d("onTextChange", charSequence.toString());
        if (isActive) {

            if (!StringEmptyUtil.isEmptyString(charSequence.toString())) {

                Bundle bundle = new Bundle();
                mFullName = charSequence.toString();
                bundle.putString(FULL_NAME, charSequence.toString());
                mFriendsFragment.setArguments(bundle);

                if (mFriendsFragment.isAdded()) {
                    mFriendsFragment.findFriend(mFullName);
                } else {
                    updateFragment(mFriendsFragment);
                }

            } else {
                if (!mMutualFriendFragment.isAdded())
                    updateFragment(mMutualFriendFragment);
            }
        }

    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }

    @Override
    public void onButtonClick(String str) {

    }
}
