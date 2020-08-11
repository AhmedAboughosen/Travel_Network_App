package com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageErrorModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters.IDisplayPostsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.displayPostsPresenters.IDisplayPostsPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector.DisplayPostsInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.commentManagementActivities.CommentActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.PostsAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.IPostsAdapterCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.PostMenuSheetDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.eventBus.DeletePostEvent;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
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

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.FRIEND_KEY;
import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.USER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostsFragment extends BaseFragment {


    public static final String POSTMODEL = "POSTMODEL";
    public static final String POST_INDEX = "PostIndex";
    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @Inject
    PostsAdapter mPostsAdapter;
    @Inject
    IDisplayPostsPresenter mDisplayPostsPresenter;

    private boolean isFinished, isLoading;
    private int mPosition;
    private String userKey;

    public PostsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_posts, container, false);
        EventBus.getDefault().register(this);

        initView(view);
        getBundleData();
        setUpInject();
        setUpRecyclerView();
        setUpPostsAdapterCallBack();
        setUpDisplayPostsPresenterCallBack();
        getPosts();
        return view;
    }

    private void setUpInject() {
        DisplayPostsInjector.getSharedInjector().injectIn(this);
    }


    private void setUpPostsAdapterCallBack() {
        mPostsAdapter.setUpPostsAdapterCallBack(new IPostsAdapterCallBack() {
            @Override
            public void userLikeThisPost(String userKey, String postKey, int position) {
                mPosition = position;
                mDisplayPostsPresenter.increasePostLikes(userKey, postKey);
            }

            @Override
            public void numberOfLikesClick(List<String> list) {
                ConfigUtil.allowToAddCurrentUser = true;
                Intent intent = new Intent(getActivity(), DisplayListOfUserActivity.class);
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, new ArrayList<>(list));
                intent.putExtra(DisplayListOfUserActivity.TITLE, "People who reacted");
                startActivity(intent);
                animateWithZoom();
            }

            @Override
            public void userDisLikeThisPost(String userKey, String postKey, int position) {
                mPosition = position;
                mDisplayPostsPresenter.decreasePostLikes(userKey, postKey);
            }

            @Override
            public void onOverFlowButtonClick(PostModel postModel, int position) {
                if (postModel.getUserPostModel().getUserInfoModel().getKeyOfUser().equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                    Bundle bundle = new Bundle();
                    bundle.putInt(POST_INDEX, position);
                    bundle.putSerializable(POSTMODEL, postModel);

                    PostMenuSheetDialog postMenuSheetDialog = new PostMenuSheetDialog();
                    postMenuSheetDialog.setArguments(bundle);
                    postMenuSheetDialog.show(getFragmentManager(), postMenuSheetDialog.getTag());
                }
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
                Toast.makeText(getActivity(), "it's me.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * get key of current user.
     */
    private void getBundleData() {
        if (getArguments() != null) {
            userKey = getArguments().getString(USER_KEY);
        }
    }


    private void getPosts() {
        mDisplayPostsPresenter.getPosts(userKey);
    }

    private void setUpRecyclerView() {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());

        mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(mPostsAdapter);
        isFinished = false;
        isLoading = true;
        setUpPaginationScroll();
    }


    private void setUpPaginationScroll() {
        mRecyclerView.addOnChildAttachStateChangeListener(new PaginationScrollListenerUtil(mRecyclerView, new IOnLoadMoreListener() {
            @Override
            public void loadMoreItems() {
                if (!isFinished) {
                    mDisplayPostsPresenter.loadMorePost();
                }
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        }));
    }


    private void setUpDisplayPostsPresenterCallBack() {
        mDisplayPostsPresenter.setUpDisplayPostsPresenterCallBack(new IDisplayPostsPresenterCallBack() {
            @Override
            public void thereIsNoDataToProvide() {
                //  mLoadFrameLayout.removeAllViews();
                ConfigUtil.activeFragment = "post";
                DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
                displayMessageFragment.setLottieAnimation(R.raw.sad_search);
                displayMessageFragment.setMessage("No Posts yet.");
                displayMessageFragment.setButtonTitle("add New Post");
                if (!userKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())){
                    displayMessageFragment.setEnabled(false);
                }

                UpdateFragmentUtil.loadFragment(displayMessageFragment, getFragmentManager(), R.id.load_post);
            }

            @Override
            public void listIsFinished() {
                isFinished = true;
            }

            @Override
            public void addFakeData() {
                mPostsAdapter.addFakeData();
            }

            @Override
            public void removeFakeData() {
                mPostsAdapter.removeFakeData();

            }

            @Override
            public void updatePostsList(List<PostModel> postModels) {
                mPostsAdapter.updateItems(postModels);
                runLayoutAnimation(mRecyclerView);
            }

            @Override
            public void updateLoading(boolean loading) {
                isLoading = loading;
            }

            @Override
            public void showMessageError(MessageErrorModel message) {
                Toast.makeText(getActivity(), message.getMessage() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void noInternetFound() {
                Toast.makeText(getActivity(), getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPostChanged(PostModel newPost, PostModel oldPost) {
                mPostsAdapter.postChanged(newPost, oldPost);
            }

            @Override
            public void addLikeSuccessful() {
                mPostsAdapter.addImageLove(mPosition, CurrentUserIDUtil.getInstance().getCurrentUserID());
            }

            @Override
            public void removeLikeSuccessful() {
                mPostsAdapter.removeImageLove(mPosition, CurrentUserIDUtil.getInstance().getCurrentUserID());
            }

        });
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onPostDeleteEvent(DeletePostEvent deletePostEvent) {
        mPostsAdapter.removePost(deletePostEvent.getIndex());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /**
     * runLayoutAnimation
     *
     * @param recyclerView
     */
    protected void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }
}
