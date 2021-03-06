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
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.interfaces.IPostsAdapterCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.viewHolders.PostsViewHolder;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.SetContentPostUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

public class PostsAdapter extends RecyclerView.Adapter<PostsViewHolder> {

    private List<PostModel> list_of_posts;
    private FragmentActivity mContext;
    private PostsViewHolder mPostsViewHolder;
    private RecyclerView mRecyclerView;
    private IPostsAdapterCallBack mPostsAdapterCallBack;


    @Inject
    public PostsAdapter(FragmentActivity context) {
        list_of_posts = new ArrayList<>();
        this.mContext = context;
    }


    @NonNull
    @Override
    public PostsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_post, parent, false);
            return new PostsViewHolder(v);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull PostsViewHolder holder, int position) {
        mPostsViewHolder = holder;

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


    public void addImageLove(int position, String userKey) {
        list_of_posts.get(position).getUserLikePost().put(userKey, true);
        notifyItemChanged(position);
    }

    public void removeImageLove(int position, String userKey) {
        list_of_posts.get(position).getUserLikePost().remove(userKey);
        notifyItemChanged(position);
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
          //  mPostsAdapterCallBack.onProfileImageClick(list_of_posts.get(position).getUserPostModel().getUserInfoModel().getKeyOfUser());
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
        if (list_of_posts.get(position).isContainImages()) {
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
    public void updateItems(List<PostModel> postModels) {
        mRecyclerView.post(() -> mContext.runOnUiThread(() -> {
            int initSize = list_of_posts.size();
            list_of_posts.addAll(postModels);
            notifyItemRangeChanged(initSize, list_of_posts.size());
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
        list_of_posts.add(ObjectIndex, newPost);
        notifyItemChanged(ObjectIndex);
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

    public void setUpPostsAdapterCallBack(IPostsAdapterCallBack mPostsAdapterCallBack) {
        this.mPostsAdapterCallBack = mPostsAdapterCallBack;
    }

}
