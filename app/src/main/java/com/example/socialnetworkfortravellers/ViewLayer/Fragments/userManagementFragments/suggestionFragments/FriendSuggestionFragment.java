package com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.suggestionFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters.IFriendSuggestionPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.FriendSuggestionPresenters.IFriendSuggestionPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.FriendSuggestionInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.FriendSuggestionAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFriendSuggestionActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFriendSuggestionAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.PaginationScrollListenerUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.IOnLoadMoreListener;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendSuggestionFragment extends BaseFragment {


    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;


    private FriendSuggestionAdapter mFriendSuggestionAdapter;
    @Inject
    UserSharedPrefManager userSharedPrefManager;
    @Inject
    IFriendSuggestionPresenter mFriendSuggestionPresenter;

    private IFriendSuggestionActivity mFriendSuggestionActivity;
    boolean isFinished, isLoading;

    public FriendSuggestionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_friend_suggestion, container, false);


        initView(view);
        setUpInject();
        setUpRecyclerView();
        setUpFriendSuggestionPresenterCallBack();
        getSuggestionFriends();
        return view;
    }

    public void setUpInject() {
        MainActivity.numberOfNotifications = 0;

        mFriendSuggestionAdapter = new FriendSuggestionAdapter(getActivity());
        mFriendSuggestionActivity = (IFriendSuggestionActivity) getActivity();
        FriendSuggestionInjector.getSharedInjector().injectIn(this);
    }

    private void getSuggestionFriends() {
        mFriendSuggestionPresenter.getSuggestionFriends(CurrentUserIDUtil.getInstance().getCurrentUserID(), userSharedPrefManager.getCountryName());
    }

    private void setUpRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        //DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        //recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.setAdapter(mFriendSuggestionAdapter);
        isFinished = false;
        isLoading = true;
        setUpPaginationScroll();

        mFriendSuggestionAdapter.setUpFriendSuggestionAdapterCallback(key -> {
            Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
            intent.putExtra("FriendKey", key);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            animateWithZoom();
        });
    }


    private void setUpPaginationScroll() {
        recyclerView.addOnChildAttachStateChangeListener(new PaginationScrollListenerUtil(recyclerView, new IOnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                if (!isFinished) {
                    mFriendSuggestionPresenter.loadMoreUser();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));
    }


    private void setUpFriendSuggestionPresenterCallBack() {
        mFriendSuggestionPresenter.setUpFriendSuggestionPresenterCallBack(new IFriendSuggestionPresenterCallBack() {
            @Override
            public void thereIsNoDataToProvide() {
                progressBar.setVisibility(View.GONE);
                mFriendSuggestionActivity.thereIsNoDataToProvide();
            }

            @Override
            public void listIsFinished() {
                progressBar.setVisibility(View.GONE);
                isFinished = true;
            }

            @Override
            public void addFakeData() {
                progressBar.setVisibility(View.GONE);
                mFriendSuggestionAdapter.addFakeData();
            }

            @Override
            public void removeFakeData() {
                progressBar.setVisibility(View.GONE);
                mFriendSuggestionAdapter.removeFakeData();
            }

            @Override
            public void updateFriendsList(List<UserModel> users) {
                progressBar.setVisibility(View.GONE);
                mFriendSuggestionAdapter.updateItems(users);
                runLayoutAnimation(recyclerView);
            }

            @Override
            public void updateLoading(boolean load) {
                progressBar.setVisibility(View.GONE);
                isLoading = load;
            }

            @Override
            public void showMessageError(MessageErrorModel message) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), message.getMessage() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void noInternetFound() {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
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
}
