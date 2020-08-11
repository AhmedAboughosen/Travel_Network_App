package com.example.socialnetworkfortravellers.ViewLayer.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.IPostsAdapterCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.MainUserTrendingViewHolder;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.PostsViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.example.socialnetworkfortravellers.utilLayer.SetContentPostUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class NewsFeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_USER_TRENDING = 0;
    private final int VIEW_TYPE_POST = 1;
    private FragmentActivity mContext;
    private List<UserModel> list_of_users;
    private List<PostModel> list_of_posts;
    private PostsViewHolder mPostsViewHolder;
    private RecyclerView mRecyclerView;
    private IPostsAdapterCallBack mPostsAdapterCallBack;

    public NewsFeedAdapter(FragmentActivity context) {
        this.mContext = context;
        list_of_posts = new ArrayList<>();
        list_of_users = new ArrayList<>();
    }

    public void setUpPostsAdapterCallBack(IPostsAdapterCallBack mPostsAdapterCallBack) {
        this.mPostsAdapterCallBack = mPostsAdapterCallBack;
    }


    /**
     * update RecyclerView with a new User
     *
     * @param users
     */
    public void updateUserItems(List<UserModel> users) {
        list_of_users.addAll(users);
        notifyDataSetChanged();
    }

    /**
     * this method is used to control which item or layout it will be displayed in recyclerview
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (list_of_posts.size() > 3 && position == 4 && list_of_users.size() >= 1)
            return VIEW_TYPE_USER_TRENDING;
        else
            return VIEW_TYPE_POST;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        if (viewType == VIEW_TYPE_USER_TRENDING) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trending_user_layout, parent, false);
            return new MainUserTrendingViewHolder(itemView);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_post, parent, false);
            return new PostsViewHolder(itemView);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof MainUserTrendingViewHolder) {
            MainUserTrendingViewHolder mainUserTrendingViewHolder = (MainUserTrendingViewHolder) holder;
            TrendingUserAdapter trendingUserAdapter = new TrendingUserAdapter(mContext);
            trendingUserAdapter.updateItems(list_of_users);
            InitializeRecyclerViewUtil.initHORIZONTALRecyclerView(mainUserTrendingViewHolder.recyclerview, mContext, trendingUserAdapter);

        } else {
            mPostsViewHolder = (PostsViewHolder) holder;

            try {
                if (list_of_posts.get(position) != null) {
           /*
            bind Full Name and profile Picture and date etc.
             */

                    mPostsViewHolder.mShimmerFrameLayout.stopShimmer();
                    mPostsViewHolder.mShimmerFrameLayout.setShimmer(null);

                    SetContentPostUtil.setDescription(mPostsViewHolder.details, list_of_posts.get(position).getDescription());
                    SetContentPostUtil.setUserInfo(mPostsViewHolder.profile_image, mPostsViewHolder.fullname, list_of_posts.get(position).getUserPostModel().getFullName(), list_of_posts.get(position).getUserPostModel().getProfilePicture(), mContext, list_of_posts.get(position).getLocationPost());
                    SetContentPostUtil.setDate(mPostsViewHolder.dateTextView, list_of_posts.get(position).getDate().toString());
                    SetContentPostUtil.setUserLikes(mPostsViewHolder.mNumberOfLikesTextView, list_of_posts.get(position).getUserLikePost().size() + "", list_of_posts.get(position).getUserLikePost(), mPostsViewHolder.likesImageView, mPostsViewHolder.likeCounterTextView, mContext);

                    mPostsViewHolder.mNumber_of_comments.setText(list_of_posts.get(position).getNumberOfComment() + " comments");
                    setImagesLayout(position);
                    setUpEvent(position);
                } else {
                    mPostsViewHolder.mShimmerFrameLayout.startShimmer();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setUpEvent(int position) {
        mPostsViewHolder.likesContainer.setOnClickListener(v -> {

            if (list_of_posts.get(position).getUserLikePost().containsKey(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
                mPostsAdapterCallBack.userDisLikeThisPost(list_of_posts.get(position).getUserPostModel().getUserInfoModel().getKeyOfUser(), list_of_posts.get(position).getPostKey(), position);
            } else {
                mPostsAdapterCallBack.userLikeThisPost(list_of_posts.get(position).getUserPostModel().getUserInfoModel().getKeyOfUser(), list_of_posts.get(position).getPostKey(), position);
            }
        });

        mPostsViewHolder.shareContainer.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, list_of_posts.get(position).getDescription() + "  this text from NetTravel App.");
            sendIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "G E E N  B O X");
            sendIntent.setType("text/plain");
            mContext.startActivity(sendIntent);

        });

        mPostsViewHolder.mNumberOfLikesTextView.setOnClickListener(v -> {

            /*
             *display activity of user
             */
            HashMap<String, Boolean> listOfKeys = list_of_posts.get(position).getUserLikePost();
            Set<String> keySet = listOfKeys.keySet();
            ArrayList list_of_user = new ArrayList<>(keySet);
            if (list_of_user.size() != 0)
                mPostsAdapterCallBack.numberOfLikesClick(list_of_user);
        });

        mPostsViewHolder.profile_image.setOnClickListener(v -> {
            mPostsAdapterCallBack.onProfileImageClick(list_of_posts.get(position).getUserPostModel().getUserInfoModel().getKeyOfUser());
        });
        mPostsViewHolder.overflow_menu.setOnClickListener(v -> mPostsAdapterCallBack.onOverFlowButtonClick(list_of_posts.get(position), position));
        mPostsViewHolder.commentsCounterContainer.setOnClickListener(v -> mPostsAdapterCallBack.onCommentClick(list_of_posts.get(position)));
    }

    /**
     * add Image Layout into flexible layout.
     *
     * @param position
     * @return
     */
    private void setImagesLayout(int position) {
           /*
            bind Image in View Pager
             */
        if (list_of_posts.get(position) != null && list_of_posts.get(position).isContainImages()) {
            mPostsViewHolder.dynamic_layout.setVisibility(View.VISIBLE);
            displayImageInPost(position);
        } else {
            mPostsViewHolder.dynamic_layout.setVisibility(View.GONE);
        }

    }


    private void displayImageInPost(int position) {
        mPostsViewHolder.dynamic_layout.removeAllViews();
        final int count = list_of_posts.get(position).getImageUrl().size();

        View ImagesPost = LayoutInflater.from(mContext).inflate(R.layout.item_images_post, null);
        ViewPager viewPager = ImagesPost.findViewById(R.id.viewpager);

        PostImagesAdapter postContainImagesAdapter = new PostImagesAdapter(mContext, list_of_posts.get(position).getImageUrl());
        viewPager.setAdapter(postContainImagesAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            public void onPageSelected(int position) {
                String ImageSelected = (position + 1) + " / " + count;
                postContainImagesAdapter.number_of_image.setText(ImageSelected);
            }
        });

        mPostsViewHolder.dynamic_layout.addView(ImagesPost);

    }

    @Override
    public void onAttachedToRecyclerView(@NotNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
    }


    /**
     * add fake data while send Request to server.
     */
    public void addFakeData() {
        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            if (list_of_posts.size() == 0 || (list_of_posts.get(list_of_posts.size() - 1) != null)) {
                list_of_posts.add(null);
                notifyItemInserted(list_of_posts.size() - 1);
            }
        }));
    }


    /**
     * this method called when we get response data from server, this mean remove progress bar
     */
    public void removeFakeData() {

        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            if (list_of_posts.size() > 0 && list_of_posts.get(list_of_posts.size() - 1) == null) {
                list_of_posts.remove(list_of_posts.size() - 1);
                notifyItemRemoved(list_of_posts.size());
            }
        }));

    }


    /**
     * update list
     *
     * @param postModels
     */
    public void updatePostsItems(List<PostModel> postModels) {
        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            int initSize = list_of_posts.size();
            list_of_posts.addAll(new ArrayList<>(postModels));
            notifyItemRangeChanged(initSize, list_of_posts.size());
        }));
    }

    /**
     * update list
     *
     * @param postModels
     */
    public void updatePostsListRefreshMode(List<PostModel> postModels) {
        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            list_of_posts.add(0, postModels.get(0));
            notifyItemRangeChanged(0, list_of_posts.size());
        }));
    }


    /**
     * when post content change such as remove or edit post.
     *
     * @param newPost
     * @param oldPost
     */
    public void postChanged(PostModel newPost, PostModel oldPost) {
        int ObjectIndex = list_of_posts.indexOf(oldPost);
        list_of_posts.remove(oldPost);
        if (ObjectIndex != -1) {
            list_of_posts.add(ObjectIndex, newPost);
            notifyItemChanged(ObjectIndex);
        }
    }

    public void addImageLove(int position, String userKey) {
        list_of_posts.get(position).getUserLikePost().put(userKey, true);
        notifyItemChanged(position);
    }

    public void removeImageLove(int position, String userKey) {
        list_of_posts.get(position).getUserLikePost().remove(userKey);
        notifyItemChanged(position);
    }


    /**
     * when  post content change such as remove or edit post.
     *
     * @param index
     */
    public void removePost(int index) {
        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            list_of_posts.remove(index);
            notifyItemRemoved(index);
            notifyItemRangeChanged(index, list_of_posts.size());
        }));


    }

    @Override
    public int getItemCount() {
        return list_of_posts.size();
    }

}
