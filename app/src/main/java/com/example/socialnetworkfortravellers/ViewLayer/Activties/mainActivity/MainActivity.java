package com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters.IMainPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.mainPresenters.IMainPresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.MainUIInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.UserProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.MessengerActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.Menu.DrawerAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.Menu.DrawerItem;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.Menu.SimpleItem;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.Menu.SpaceItem;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.AddPostActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.AddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.FriendTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.notificationFragments.NotificationFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.NewsFeedFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.suggestionFragments.FriendSuggestionFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFriendSuggestionActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;
import com.example.socialnetworkfortravellers.eventBus.ListOfNotificationsEvent;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.NotificationManager;
import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements DrawerAdapter.OnItemSelectedListener, IFriendSuggestionActivity, IMessageFragment {


    private SlidingRootNav slidingRootNav;
    private static final int POS_HOME = 0;
    private static final int POS_ADD_NEW_POST = 1;
    private static final int POS_PROFILE = 2;
    private static final int POS_FIND_FRIENDS = 3;
    private static final int POS_MESSAGES = 4;
    private static final int POS_ADD_NEW_TRIP = 5;
    private static final int POS_TRAVEL_BODDY = 6;
    private static final int POS_SETTINGS = 7;
    private static final int POS_LOGOUT = 9;

    public static int numberOfNotifications = 0;

    private DrawerAdapter drawadapter;

    @BindView(R.id.navigation)
    AHBottomNavigation bottomNavigationViewEx;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private String[] screenTitles;
    private Drawable[] screenIcons;
    private FirebaseAuth mAuth;
    public static String FRIENDKEY = "";

    @Inject
    UserSharedPrefManager sharedPrefManager;
    @Inject
    NotificationManager notificationManager;
    @Inject
    IMainPresenter mMainPresenter;

    private NotificationFragment notificationFragment;
    private FriendSuggestionFragment friendSuggestionFragment;
    private NewsFeedFragment newsFeedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        try {
            ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.MAIN);
            EventBus.getDefault().register(this);
            initView();
            setUpInject();
            initializeToolbar();
            initializeSlidingRootNav(savedInstanceState);
            setupBottomNavigationView();
            UserInfo();
            setUpFragment();
            loadFragment(newsFeedFragment);
            setUpNotifications();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * setUp Inject
     */
    private void setUpInject() {
        mAuth = FirebaseAuth.getInstance();
        MainUIInjector.getSharedInjector().injectIn(this);
    }


    /**
     * set Up toolbar then layout to toolbar.
     */
    private void initializeToolbar() {
        try {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Travel Feed");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * set user name and profile picture of user.
     */
    private void UserInfo() {
        try {
            CircleImageView nav_profile_image = findViewById(R.id.nav_profile_image);
            TextView nav_user_name = findViewById(R.id.nav_user_name);

            if (!StringEmptyUtil.isEmptyString(sharedPrefManager.getFullName()))
                nav_user_name.setText(sharedPrefManager.getFullName());
            if (!StringEmptyUtil.isEmptyString(sharedPrefManager.getImageUrl()))
                Glide.with(Objects.requireNonNull(getApplicationContext())).load(sharedPrefManager.getImageUrl()).into(nav_profile_image);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * load news feed fragment to user.
     */
    private void setUpFragment() {
        try {
            newsFeedFragment = new NewsFeedFragment();
            friendSuggestionFragment = new FriendSuggestionFragment();
            notificationFragment = new NotificationFragment();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * initializeSlidingRootNav
     *
     * @param savedInstanceState
     */
    private void initializeSlidingRootNav(Bundle savedInstanceState) {
        try {
            slidingRootNav = new SlidingRootNavBuilder(this)
                    .withToolbarMenuToggle(toolbar)
                    .withMenuOpened(false)
                    .withContentClickableWhenMenuOpened(true)
                    .withSavedState(savedInstanceState)
                    .withMenuLayout(R.layout.navigation_drawer_content)
                    .inject();
            initializeRecyclerView();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void initializeRecyclerView() {

        try {
            screenIcons = loadScreenIcons();
            screenTitles = loadScreenTitles();

            drawadapter = new DrawerAdapter(Arrays.asList(
                    createItemFor(POS_HOME).setChecked(true),
                    createItemFor(POS_ADD_NEW_POST),
                    createItemFor(POS_PROFILE),
                    createItemFor(POS_FIND_FRIENDS),
                    createItemFor(POS_MESSAGES),
                    createItemFor(POS_ADD_NEW_TRIP),
                    createItemFor(POS_TRAVEL_BODDY),
                    createItemFor(POS_SETTINGS),
                    new SpaceItem(48),
                    createItemFor(POS_LOGOUT)));
            drawadapter.setListener(this);

            RecyclerView list = findViewById(R.id.list);
            list.setNestedScrollingEnabled(false);
            list.setLayoutManager(new LinearLayoutManager(this));
            list.setAdapter(drawadapter);

            drawadapter.setSelected(POS_HOME);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    /**
     * create item with text and icon and color.
     *
     * @param position
     * @return
     */
    private DrawerItem createItemFor(int position) {
        return new SimpleItem(screenIcons[position], screenTitles[position])
                .withIconTint(color(R.color.textColorSecondary))
                .withTextTint(color(R.color.textColorPrimary))
                .withSelectedIconTint(color(R.color.colorAccent))
                .withSelectedTextTint(color(R.color.colorAccent));
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }


    private String[] loadScreenTitles() {
        return getResources().getStringArray(R.array.ld_activityScreenTitles);
    }

    private Drawable[] loadScreenIcons() {
        TypedArray ta = getResources().obtainTypedArray(R.array.ld_activityScreenIcons);
        Drawable[] icons = new Drawable[ta.length()];
        try {

            for (int i = 0; i < ta.length(); i++) {
                int id = ta.getResourceId(i, 0);
                if (id != 0) {
                    icons[i] = ContextCompat.getDrawable(this, id);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        ta.recycle();
        return icons;
    }


    @Override
    public void onItemSelected(int position) {

        try {

            if (position == POS_ADD_NEW_POST) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), AddPostActivity.class);
                animateWithZoom();
            }
            if (position == POS_FIND_FRIENDS) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), FindFriendActivity.class);
                animateWithZoom();
            }

            if (position == POS_PROFILE) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), UserProfileActivity.class);
            }


            if (position == POS_ADD_NEW_TRIP) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), AddTripActivity.class);
            }


            if (position == POS_TRAVEL_BODDY) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), FriendTripActivity.class);
                animateWithZoom();
            }

            if (position == POS_SETTINGS) {
                //   Intent i =new Intent(getApplication(), AdvertiseUs.class);
                //  startActivity(i);
            }

            if (position == POS_MESSAGES) {
                SendToActivityUtil.getInstance().SendUserToOtherActivity(getApplicationContext(), MessengerActivity.class);
                animateWithZoom();
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.putExtra(Intent.EXTRA_TEXT, " Click to download Colors Soda app from wwww. ");
//                sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "G E E N  B O X");
//                sendIntent.setType("text/plain");
//                startActivity(sendIntent);

            }

            if (position == POS_LOGOUT) {
                mAuth.signOut();
                SendUserToLoginActivity();
            }
            slidingRootNav.closeMenu();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (drawadapter != null)
            drawadapter.setSelected(POS_HOME);
    }

    private void SendUserToLoginActivity() {
        try {
            Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
            finishAffinity();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }


    private void setupBottomNavigationView() {

        // Create items
        AHBottomNavigationItem homeItem = new AHBottomNavigationItem(R.string.homeItem, R.drawable.ic_home, R.color.FirstColorAccent);
        AHBottomNavigationItem exploreItem = new AHBottomNavigationItem(R.string.exploreItem, R.drawable.ic_find_friends, R.color.SecondColorAccent);
        AHBottomNavigationItem notificationItem = new AHBottomNavigationItem(R.string.notificationItem, R.drawable.ic_notification, R.color.ThridColorAccent);


        bottomNavigationViewEx.addItem(homeItem);
        bottomNavigationViewEx.addItem(exploreItem);
        bottomNavigationViewEx.addItem(notificationItem);
        bottomNavigationViewEx.setTranslucentNavigationEnabled(true);


        bottomNavigationViewEx.setColored(true);

        bottomNavigationViewEx.setOnTabSelectedListener((position, wasSelected) -> {
            // Do something cool here...
            try {

                switch (position) {
                    case 0:
                        getSupportActionBar().setTitle("Travel Feed");
                        if (newsFeedFragment.isAdded()) {
                            newsFeedFragment.smoothScrollToPosition();
                        }

                        loadFragment(newsFeedFragment);
                        return true;
                    case 1:
                        getSupportActionBar().setTitle("Explore");
                        loadFragment(friendSuggestionFragment);

                        return true;
                    case 2:
                        getSupportActionBar().setTitle("Notification");
                        loadFragment(notificationFragment);
                        return true;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            return false;
        });


    }


    private void loadFragment(Fragment fragment) {
        UpdateFragmentUtil.loadFragment(fragment,  getSupportFragmentManager(),R.id.main_container);
    }

    @Override
    public void thereIsNoDataToProvide() {
        DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
        displayMessageFragment.setMessage("we can't find  friends which matches your interest or country.");
        displayMessageFragment.setLottieAnimation(R.raw.friendships);
        displayMessageFragment.setButtonTitle("find Friends");
        UpdateFragmentUtil.loadFragment(displayMessageFragment, R.id.main_container, getSupportFragmentManager());
    }

    @Override
    public void onButtonClick(String str) {
        SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(MainActivity.this, FindFriendActivity.class);
    }


    private void setUpNotifications() {
        mMainPresenter.setUpMainPresenterCallback(new IMainPresenterCallback() {
            @Override
            public void showMessageError(String message) {
                Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void networkErrorMessage() {
                Toast.makeText(MainActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();

            }
        });

        mMainPresenter.getNotifications(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onNotificationReceived(ListOfNotificationsEvent listOfNotificationsEvent) {
        ++numberOfNotifications;
        FRIENDKEY = listOfNotificationsEvent.getList().getFriendKey();
        if (notificationFragment != null && !notificationFragment.isAdded()) {
            notificationManager.createNotificationInApp(listOfNotificationsEvent.getList().getFullName(), listOfNotificationsEvent.getList().getFriendKey());
        }

        AHNotification notification = new AHNotification.Builder()
                .setText(numberOfNotifications + "")
                .setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorRed))
                .setTextColor(ContextCompat.getColor(MainActivity.this, R.color.white))
                .build();
        bottomNavigationViewEx.setNotification(notification, 2);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        mMainPresenter.removeEventListener();
    }
}
