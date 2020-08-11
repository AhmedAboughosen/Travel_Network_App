package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters.IGetListOfUserPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.getListOfUserPresenters.IGetListOfUserPresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.ActiveUserInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.UserProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.ActiveUserAdapter;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DisplayListOfUserActivity extends BaseActivity {


    public static final String LIST_OF_KEYS = "ListOfKeys";
    public static final String TITLE = "Title";
    private List<String> userKeys;

    private List<UserModel> list_of_user;
    private String mTitle;

    @BindView(R.id.usereRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.title)
    TextView mTitleTextView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    IGetListOfUserPresenter mGetListOfUserPresenter;
    @Inject
    ActiveUserAdapter mActiveUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_user);

        try {
            setUpInject();
            ButterKnife.bind(this);
            setSupportActionBar(mToolbar);
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
            getDataIntent();
            setUpRecyclerView();
            setUpActiveUserAdapterCallback();
            getListOfUser();

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }

    private void setUpInject() {
        ActiveUserInjector.getSharedInjector().injectIn(this);
    }


    /**
     * getDataIntent
     */
    private void getDataIntent() {
        if (getIntent().getExtras() != null) {
            userKeys = getIntent().getStringArrayListExtra(LIST_OF_KEYS);
            mTitle = getIntent().getStringExtra(TITLE);
            mTitleTextView.setText(mTitle);
        }
    }


    /**
     * this method used to get user from keys
     */
    private void getListOfUser() {
        mGetListOfUserPresenter.setUpGetListOfUserPresenterCallback(new IGetListOfUserPresenterCallback() {
            @Override
            public void showMessageError(String message) {
                if ("we can't find friends.".equals(message)) {
                    Toast.makeText(DisplayListOfUserActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                    animateWithZoom();
                } else {
                    showMessagesError(message);
                }
            }

            @Override
            public void networkErrorMessage() {
                showMessagesError(getString(R.string.no_internet));
            }


            @Override
            public void ListOfUsers(List<UserModel> userModels) {
                mActiveUserAdapter.updateItems(userModels);
            }
        });

        mGetListOfUserPresenter.getUsers(userKeys);
    }


    /**
     * setUpRecyclerView
     */
    private void setUpRecyclerView() {
        list_of_user = new ArrayList<>();
        list_of_user.add(null);
        list_of_user.add(null);
        mActiveUserAdapter.updateItems(list_of_user);
        InitializeRecyclerViewUtil.initVerticalRecyclerViewWithLine(recyclerView, getApplicationContext(), mActiveUserAdapter);
    }


    /**
     * setUpPeopleWhoLikeCallback
     */
    private void setUpActiveUserAdapterCallback() {
        mActiveUserAdapter.setUpActiveUserCallback(userKey -> {

            if (!userKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                Intent intent = new Intent(DisplayListOfUserActivity.this, FriendProfileActivity.class);
                intent.putExtra("FriendKey", userKey);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            } else {
                Intent intent = new Intent(DisplayListOfUserActivity.this, UserProfileActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
            animateWithZoom();
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
