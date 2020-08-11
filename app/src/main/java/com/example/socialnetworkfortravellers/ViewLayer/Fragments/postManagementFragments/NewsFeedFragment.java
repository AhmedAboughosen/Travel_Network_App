package com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IPostsNewsFeedPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IPostsNewsFeedPresenterCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IUserTrendingAndNearByPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.NewsFeedPresenters.IUserTrendingAndNearByPresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.NewsFeedInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities.CommentActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.NewsFeedAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.TravellersNearbyAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.IPostsAdapterCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.PostForFriendMenuSheetDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.nodesLayer.HidePostEvent;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.example.socialnetworkfortravellers.utilLayer.PaginationScrollListenerUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.IOnLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment.POST_INDEX;
import static com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil.NO_USERS_IN_YOUR_COUNTRY_EXISTS;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFeedFragment extends BaseFragment {


    @BindView(R.id.nearby_RecyclerView)
    LinearLayout mLinearLayout;

    @BindView(R.id.posts_RecyclerView)
    RecyclerView mPostsRecyclerView;


    @BindView(R.id.nearby)
    TextView mNearbyTextView;

    @BindView(R.id.progress_bar_for_travellers)
    ProgressBar mProgressBarForTravellers;


    @BindView(R.id.newsFeedProgressBar)
    ProgressBar mNewsFeedProgressBar;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    private boolean isFinished, isLoading;

    @Inject
    IUserTrendingAndNearByPresenter mGetUsersFromCountryPresenter;

    @Inject
    IPostsNewsFeedPresenter mPostsNewsFeedPresenter;

    NewsFeedAdapter newsFeedAdapter;
    @Inject
    UserSharedPrefManager mUserSharedPrefManager;
    private int mPosition;


    public NewsFeedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news_feed, container, false);
        EventBus.getDefault().register(this);

        setUpInject();
        initView(view);
        setUpView();
        setUpUserTrendingAndNearByPresenterCallback();
        setUpPostsNewsFeedPresenterCallback();
        setUpPostsAdapterCallBack();
        // setUpSwipeRefreshLayout();
        setUpNewsFeedRecyclerView();
        getUserNearBy();
        getUserRating();
        loadNewsFeedPosts();
        return view;
    }


    private void setUpView() {
        newsFeedAdapter = new NewsFeedAdapter(getActivity());
    }

    private void setUpInject() {
        NewsFeedInjector.getSharedInjector().injectIn(this);
    }


    private void setUpSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary), getResources().getColor(R.color.green));
        mSwipeRefreshLayout.setOnRefreshListener(() -> mPostsNewsFeedPresenter.onPostsRefresh());
    }


    private void setUpNewsFeedRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mPostsRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mPostsRecyclerView.getContext(), linearLayoutManager.getOrientation());


        mPostsRecyclerView.addItemDecoration(dividerItemDecoration);
        mPostsRecyclerView.setAdapter(newsFeedAdapter);
        isFinished = false;
        isLoading = true;
        setUpPaginationScroll();
    }

    private void setUpPaginationScroll() {
        mPostsRecyclerView.addOnChildAttachStateChangeListener(new PaginationScrollListenerUtil(mPostsRecyclerView, new IOnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                if (!isFinished) {
                    mPostsNewsFeedPresenter.loadMorePost();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));
    }

    private void getUserNearBy() {
        mGetUsersFromCountryPresenter.getUserNearBy(mUserSharedPrefManager.getCountryName());
    }

    private void getUserRating() {
        mGetUsersFromCountryPresenter.getUserRating();
    }

    private void setUpUserTrendingAndNearByPresenterCallback() {
        mGetUsersFromCountryPresenter.setUpUserTrendingAndNearByPresenterCallback(new IUserTrendingAndNearByPresenterCallback() {
            @Override
            public void ListOfUsersForNearByTravellers(List<UserModel> userModels) {
                mProgressBarForTravellers.setVisibility(View.GONE);

                if (userModels.size() == 0) {
                    mNearbyTextView.setVisibility(View.GONE);
                    mLinearLayout.setVisibility(View.GONE);
                    return;
                }
                if (getActivity() != null) {
                    mLinearLayout.removeAllViews();
                    RecyclerView recyclerView = new RecyclerView(getActivity());
                    recyclerView.setLayoutParams(
                            new ViewGroup.LayoutParams(
                                    // or ViewGroup.LayoutParams.WRAP_CONTENT
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    // or ViewGroup.LayoutParams.WRAP_CONTENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));

                    mLinearLayout.addView(recyclerView);
                    InitializeRecyclerViewUtil.initHORIZONTALRecyclerView(recyclerView, getActivity(), new TravellersNearbyAdapter(userModels, getActivity()));
                    runLayoutAnimation(recyclerView);
                }

            }

            @Override
            public void ListOfUsersForUserTrending(List<UserModel> userModels) {
                newsFeedAdapter.updateUserItems(userModels);
            }


            @Override
            public void showMessageError(String message) {
                mProgressBarForTravellers.setVisibility(View.GONE);
                if (NO_USERS_IN_YOUR_COUNTRY_EXISTS.equals(message)) {
                    mNearbyTextView.setVisibility(View.GONE);
                    mLinearLayout.setVisibility(View.GONE);
                }

                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                mNearbyTextView.setVisibility(View.GONE);
                mLinearLayout.setVisibility(View.GONE);
                mProgressBarForTravellers.setVisibility(View.GONE);
                showMessageError(getString(R.string.no_internet));
            }
        });
    }

    private void setUpPostsAdapterCallBack() {
        newsFeedAdapter.setUpPostsAdapterCallBack(new IPostsAdapterCallBack() {
            @Override
            public void userLikeThisPost(String userKey, String postKey, int position) {
                mPosition = position;
                mPostsNewsFeedPresenter.increasePostLikes(userKey, postKey);
            }

            @Override
            public void numberOfLikesClick(List<String> list_of_user) {
                ConfigUtil.allowToAddCurrentUser = true;
                Intent intent = new Intent(getActivity(), DisplayListOfUserActivity.class);
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, new ArrayList<>(list_of_user));
                intent.putExtra(DisplayListOfUserActivity.TITLE, "People who reacted");
                startActivity(intent);
                animateWithZoom();
            }


            @Override
            public void userDisLikeThisPost(String userKey, String postKey, int position) {
                mPosition = position;
                mPostsNewsFeedPresenter.decreasePostLikes(userKey, postKey);
            }

            @Override
            public void onOverFlowButtonClick(PostModel postModel, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt(POST_INDEX, position);

                PostForFriendMenuSheetDialog postMenuSheetDialog = new PostForFriendMenuSheetDialog();
                postMenuSheetDialog.setArguments(bundle);
                postMenuSheetDialog.show(getFragmentManager(), postMenuSheetDialog.getTag());
            }

            @Override
            public void onCommentClick(PostModel postModel) {
                Intent intent = new Intent(getActivity(), CommentActivity.class);

                intent.putExtra(CommentActivity.POST_MODEL, postModel);

                startActivity(intent);
                animateWithZoom();
            }

            @Override
            public void onProfileImageClick(String key) {
                Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
                intent.putExtra("FriendKey", key);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                startActivity(intent);
                animateWithZoom();
            }
        });
    }


    public void loadNewsFeedPosts() {
        mPostsNewsFeedPresenter.getPostOfFriends(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }

    private void setUpPostsNewsFeedPresenterCallback() {
        mPostsNewsFeedPresenter.setUpPostsNewsFeedPresenterCallback(new IPostsNewsFeedPresenterCallback() {

            @Override
            public void thereIsNoUserForNewsFeed() {
                mNewsFeedProgressBar.setVisibility(View.GONE);
                DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
                displayMessageFragment.setLottieAnimation(R.raw.sad_search);
                displayMessageFragment.setMessage("No Posts yet.");
                displayMessageFragment.setButtonTitle("find buddies");
                UpdateFragmentUtil.loadFragment(displayMessageFragment, getFragmentManager(), R.id.load_post);
            }

            @Override
            public void listIsFinished() {
                isFinished = true;
            }

            @Override
            public void addFakeData() {
                mNewsFeedProgressBar.setVisibility(View.GONE);
                newsFeedAdapter.addFakeData();
            }

            @Override
            public void removeFakeData() {
                mNewsFeedProgressBar.setVisibility(View.GONE);
                newsFeedAdapter.removeFakeData();
            }

            @Override
            public void updatePostsList(List<PostModel> postModels) {
                mNewsFeedProgressBar.setVisibility(View.GONE);
                newsFeedAdapter.updatePostsItems(postModels);
                runLayoutAnimation(mPostsRecyclerView);
            }

            @Override
            public void updatePostsListRefreshMode(List<PostModel> postModels) {
                mSwipeRefreshLayout.setRefreshing(false);
                newsFeedAdapter.updatePostsListRefreshMode(postModels);
            }

            @Override
            public void updateLoading(boolean loading) {
                isLoading = loading;
            }

            @Override
            public void onPostChanged(PostModel newPost, PostModel oldPost) {
                mNewsFeedProgressBar.setVisibility(View.GONE);
                newsFeedAdapter.postChanged(newPost, oldPost);
            }

            @Override
            public void addLikeSuccessful() {
                newsFeedAdapter.addImageLove(mPosition, CurrentUserIDUtil.getInstance().getCurrentUserID());
            }

            @Override
            public void removeLikeSuccessful() {
                newsFeedAdapter.removeImageLove(mPosition, CurrentUserIDUtil.getInstance().getCurrentUserID());
            }

            @Override
            public void showMessageError(String message) {
                Toast.makeText(getActivity(), message + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
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

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onHidePostEvent(HidePostEvent hidePostEvent) {
        if (newsFeedAdapter != null)
            newsFeedAdapter.removePost(hidePostEvent.getIndex());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void smoothScrollToPosition() {
        mPostsRecyclerView.smoothScrollToPosition(0);
    }
}
