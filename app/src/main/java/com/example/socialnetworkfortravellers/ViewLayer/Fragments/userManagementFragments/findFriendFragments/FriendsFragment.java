package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.FoundUserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IFindFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.findFriendsPresenters.IFindFriendPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.FriendsInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.FoundFriendsAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFindFriendActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFoundFriendsAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.PaginationScrollListenerUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.IOnLoadMoreListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
    private String mFullName;


    @Inject
    public IFindFriendPresenter mFindFriendPresenter;
    private FoundFriendsAdapter mFoundFriendsAdapter;
    private boolean isLoading, isFinished;
    private IFindFriendActivity mFindFriendActivity;
    private List<FoundUserModel> mListOfUserName;

    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        try {
            setUpInjector();
            initView(view);
            getFullName();
            setUpRecyclerView();
            setUpFindFriendPresenterCallBack();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    private void getFullName() {
        if (getArguments() != null)
            mFullName = getArguments().getString(FindFriendActivity.FULL_NAME);
        else
            mFullName = "";
    }

    public void findFriend(String fullName) {
        mFullName = fullName;
        isFinished = false;
        mFindFriendPresenter.reSetIndex();
        mFoundFriendsAdapter.removeAllItems();
        mFindFriendPresenter.findFriend(mFullName);
    }

    private void setUpInjector() {
        FriendsInjector.getSharedInjector().injectIn(this);
    }


    private void setUpRecyclerView() {
        mFoundFriendsAdapter = new FoundFriendsAdapter(getActivity());

        mFoundFriendsAdapter.setUpFoundFriendsAdapterCallback(key -> {
            Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
            intent.putExtra("FriendKey",key);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            animateWithZoom();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);


        recyclerView.setAdapter(mFoundFriendsAdapter);
        isFinished = false;
        setUpPaginationScroll();
    }


    private void setUpPaginationScroll() {
        recyclerView.addOnChildAttachStateChangeListener(new PaginationScrollListenerUtil(recyclerView, new IOnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                if (!isFinished) {
                    mFindFriendPresenter.getUser();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));

    }

    private void setUpFindFriendPresenterCallBack() {
        mFindFriendPresenter.setUpFindFriendPresenterCallBack(new IFindFriendPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                mFindFriendActivity.internetIsNotConnected();
            }

            @Override
            public void noFriendExists() {
                mFindFriendActivity.noFriendExists();
            }


            @Override
            public void addFakeData() {
                mFoundFriendsAdapter.addFakeData();
            }

            @Override
            public void removeFakeData() {
                mFoundFriendsAdapter.removeFakeData();
            }

            @Override
            public void updateFriendsList(List<UserModel> users) {
                mFoundFriendsAdapter.updateItems(users);
            }

            @Override
            public void updateLoading(boolean loadingState) {
                isLoading = loadingState;
            }

            @Override
            public void listIsFinished() {
                isFinished = true;
            }

        });
        mFindFriendPresenter.AllUserNames(this.mListOfUserName);
        mFindFriendPresenter.findFriend(mFullName);
    }


    public void setListOfUserName(List<FoundUserModel> mListOfUserName) {
        this.mListOfUserName = mListOfUserName;
    }

    public void setUpFriendActivity(IFindFriendActivity mFriendsFragmentCallBack) {
        this.mFindFriendActivity = mFriendsFragmentCallBack;
    }
}
