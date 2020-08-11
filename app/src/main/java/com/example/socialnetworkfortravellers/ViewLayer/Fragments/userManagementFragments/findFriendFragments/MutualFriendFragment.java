package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters.IMutualFriendPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.mutualFriendPresenters.IMutualFriendPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.MutualFriendInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.FoundFriendsAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
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
public class MutualFriendFragment extends BaseFragment {


    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    private FoundFriendsAdapter mFoundFriendsAdapter;
    private boolean isLoading, isFinished;
    @Inject
    IMutualFriendPresenter mMutualFriendPresenter;
    private IFindFriendActivity mFindFriendActivity;

    public MutualFriendFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mutual_friend, container, false);

        initView(view);
        setUpInjector();
        initView();
        setUpMutualFriendPresenterCallBack();
        setUpRecyclerView();
        return view;
    }


    private void setUpInjector() {
        MutualFriendInjector.getSharedInjector().injectIn(this);
    }

    /**
     * initView
     */
    private void initView() {
        mFindFriendActivity = (IFindFriendActivity) getActivity();
        mMutualFriendPresenter.reSetIndex();
        isFinished = false;
        isLoading = false;

    }

    /**
     * setUpListOfImageRecyclerView
     */
    private void setUpRecyclerView() {
        mFoundFriendsAdapter = new FoundFriendsAdapter(getActivity());
        mFoundFriendsAdapter.setUpFoundFriendsAdapterCallback(key -> {
            //go to the profile of user
            Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
            intent.putExtra("FriendKey",key);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            animateWithZoom();
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);


        mRecyclerView.setAdapter(mFoundFriendsAdapter);
        setUpPaginationScroll();
    }


    /**
     * setUpPaginationScroll
     */
    private void setUpPaginationScroll() {
        mRecyclerView.addOnChildAttachStateChangeListener(new PaginationScrollListenerUtil(mRecyclerView, new IOnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                if (!isFinished) {
                    mMutualFriendPresenter.getUser();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));

    }


    private void setUpMutualFriendPresenterCallBack() {
        mMutualFriendPresenter.setUpMutualFriendPresenterCallBack(new IMutualFriendPresenterCallBack() {

            @Override
            public void currentUserDoesNotHaveFriends() {

                /*
                 * create fragment to put here
                 */
                mFindFriendActivity.currentUserDoesNotHaveFriends();
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

            @Override
            public void showMessageError(String message) {
                BaseProgressDialog.getInstance().showMessagesError(message, getActivity());
            }

            @Override
            public void noInternetFound() {
                mFindFriendActivity.internetIsNotConnected();
            }
        });

        mMutualFriendPresenter.getFriendsOfCurrentUser();
    }
}
