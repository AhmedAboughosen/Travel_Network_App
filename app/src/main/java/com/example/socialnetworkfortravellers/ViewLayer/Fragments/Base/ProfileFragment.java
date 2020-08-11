package com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base;

import android.content.Intent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TabModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserInfoModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters.IProfilePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.userProfilePresenters.IProfilePresenterCallBack;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.DisplayProfilePictureActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.DisplayInterestAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.ProfileTabsAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.ICheckNetworkConnectivity;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.example.socialnetworkfortravellers.utilLayer.DisplayMessageToast;
import com.example.socialnetworkfortravellers.utilLayer.FlexibleColorUtli;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringUtil;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.DisplayProfilePictureActivity.PROFILE_IMAGE;

public abstract class ProfileFragment extends BaseFragment {
    protected String profileImage, fullName;
    protected ICheckNetworkConnectivity mCheckNetworkConnectivity;
    protected UserModel mUserModel;
    protected List<String> listOfFollowersOfUser;
    protected List<String> listOfFollowingsOfUser;
    private String mCurrentUser;

    protected abstract void loadUserSuccessfully();

    @Inject
    IProfilePresenter mIProfilePresenter;
    @Inject
    DisplayInterestAdapter mDisplayInterestAdapter;


    public @BindView(R.id.flexible_color)
    LinearLayout flexible_color;


    public @BindView(R.id.list)
    RecyclerView mList_of_interest;

    public @BindView(R.id.user_profile_Image_shimmer)
    ShimmerFrameLayout user_profile_Image_shimmer;

    public @BindView(R.id.user_profile_info_shimmer)
    ShimmerFrameLayout user_profile_shimmer;

    public @BindView(R.id.arrow_back)
    ImageView arrow_back;
    public @BindView(R.id.nav_user_name)
    TextView user_name;
    public @BindView(R.id.profile_image)
    CircleImageView profile_image;
    public @BindView(R.id.number_of_Followers)
    TextView number_of_Followers;

    @BindView(R.id.viewPager_container)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.bio)
    TextView bio;
    @BindView(R.id.Joined_date)
    TextView Joined_date;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.number_of_posts)
    TextView number_of_posts;
    @BindView(R.id.number_of_following)
    TextView number_of_following;
    @BindView(R.id.flag)
    CircleImageView flag;


    @OnClick(R.id.arrow_back)
    public void onArrowBack() {
        mCheckNetworkConnectivity.exits();
    }

    @OnClick(R.id.profile_image)
    public void onProfileClick() {
        if (mUserModel != null && !StringEmptyUtil.isEmptyString(mUserModel.getProfilePicture())) {
            Intent intent = new Intent(getActivity(), DisplayProfilePictureActivity.class);
            intent.putExtra(PROFILE_IMAGE, mUserModel.getProfilePicture());
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), "this user doesn't set up Profile Image", Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.followingButton)
    public void onFollowingButtonClick() {
        startWaiting("Please wait", false);
        mIProfilePresenter.getAllFollowingsOfUser(mCurrentUser);
    }


    @OnClick(R.id.FollowersButton)
    public void onFollowersButtonClick() {
        startWaiting("Please wait", false);
        mIProfilePresenter.getAllFollowersOfUser(mCurrentUser);
    }


    @Override
    public void onPause() {
        super.onPause();
        mIProfilePresenter.removeEventListener();
    }

    /**
     * this Method used to get user object from server.
     */
    protected void getUserData(String userKey) {

        this.mCurrentUser = userKey;
        this.mIProfilePresenter.setUpProfilePresenterCallBack(new IProfilePresenterCallBack() {
            @Override
            public void userData(UserModel userModel) {
                mUserModel = userModel;
                loadUserSuccessfully();
                setUserData(userModel);
            }

            @Override
            public void ExceptionMessage(String message) {
                if (getContext() != null) {
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void noUser(String message) {
                BaseProgressDialog.getInstance().showMessagesError(message, getActivity());
            }

            @Override
            public void selectedInterest(List<String> list) {
                setUpSelectedInterest(list);
            }

            @Override
            public void userCounterData(UserInfoModel userInfoModel) {
                setUserCount(userInfoModel);
            }

            @Override
            public void internetIsNotConnected() {
                mCheckNetworkConnectivity.noInternet();
            }

            @Override
            public void listOfFollowersOfUser(List<String> lis) {
                finishWaiting();
                Intent intent = new Intent(getActivity(), DisplayListOfUserActivity.class);
                ConfigUtil.allowToAddCurrentUser = true;
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, new ArrayList<>(lis));
                intent.putExtra(DisplayListOfUserActivity.TITLE, "Followers");
                startActivity(intent);
                animateWithZoom();
            }

            @Override
            public void listOfFollowingsOfUser(List<String> lis) {
                finishWaiting();
                ConfigUtil.allowToAddCurrentUser = true;
                Intent intent = new Intent(getActivity(), DisplayListOfUserActivity.class);
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, new ArrayList<>(lis));
                intent.putExtra(DisplayListOfUserActivity.TITLE, "Followings");
                startActivity(intent);
                animateWithZoom();
            }

        });
        this.mIProfilePresenter.getUser(userKey);
        this.mIProfilePresenter.getUserCounter(userKey);
        this.mIProfilePresenter.getUserInterest(userKey);
    }


    private void setUpSelectedInterest(List<String> list) {
        mDisplayInterestAdapter.getInterests().clear();
        mDisplayInterestAdapter.getInterests().addAll(list);
        InitializeRecyclerViewUtil.initHORIZONTALRecyclerView(mList_of_interest, getContext(), mDisplayInterestAdapter);
    }


    /**
     * initView Tab Layout , add a new tabs
     */
    protected void intiTabLayout(List<TabModel> tabs) {
        try {

            for (int i = 0; i < tabs.size(); ++i) {
                AddTab(tabs.get(i).getNameofTab(), tabs.get(i).getIndexofTab());
            }

            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
            Objects.requireNonNull(tabLayout.getTabAt(0)).select();


            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
            DisplayMessageToast.MakeMessage(getContext(), "there is something wrong", Toast.LENGTH_SHORT);
        }

    }


    /**
     * this method is used to add tab to TabLayout
     *
     * @param text
     * @param position
     */
    private void AddTab(String text, int position) {
        try {
            TabLayout.Tab Tab = tabLayout.newTab();
            Tab.setText(text);
            tabLayout.addTab(Tab, position); // add  the tab at in the TabLayout
        } catch (Exception ex) {
            ex.printStackTrace();
            DisplayMessageToast.MakeMessage(getContext(), "there is something wrong", Toast.LENGTH_SHORT);
        }

    }


    /**
     * this method used to control View Pager in profile activity
     */
    protected void initViewPager(List<Fragment> mFragmentList) {
        try {
            ProfileTabsAdapter adapter = new ProfileTabsAdapter(getChildFragmentManager());
            adapter.getFragments().addAll(mFragmentList);

            viewPager.setAdapter(adapter);
        } catch (Exception ex) {
            ex.printStackTrace();
            DisplayMessageToast.MakeMessage(getContext(), StringConfigUtil.MESSAGE_PROBLEM, Toast.LENGTH_SHORT);
        }
    }


    /**
     * when data received this method will called and bind data with UI
     *
     * @param userModel
     */
    public void setUserCount(UserInfoModel userModel) {

        try {
            if (userModel != null) {
                number_of_Followers.setText(userModel.getNumber_Of_Followers() + "");
                number_of_following.setText(userModel.getNumber_Of_Following() + "");
                number_of_posts.setText(userModel.getNumber_Of_Posts() + "");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            DisplayMessageToast.MakeMessage(getContext(), "there is something wrong", Toast.LENGTH_SHORT);
        }
    }


    /**
     * set data of user in view
     *
     * @param userModel
     */
    public void setUserData(UserModel userModel) {

        try {
            /*
            stop Shimmer
             */
            user_profile_Image_shimmer.stopShimmer();
            user_profile_Image_shimmer.setShimmer(null);
            user_profile_shimmer.stopShimmer();
            user_profile_shimmer.setShimmer(null);


            if (userModel != null) {
                /*
                set full name and bio
                 */
                this.profileImage = userModel.getProfilePicture();
                user_name.setBackground(null);
                this.fullName = userModel.getFullName();
                user_name.setText(this.fullName);
                bio.setBackground(null);
                bio.setText((StringUtil.isEmptyString(userModel.getBio())) ? "" : userModel.getBio());

                /*
                set profile image
                 */
                profile_image.setBackground(null);
                if (!StringUtil.isEmptyString(this.profileImage)) {
                    Glide.with(Objects.requireNonNull(getActivity())).load(this.profileImage).into(profile_image);
                    FlexibleColorUtli.setFlexible_color(this.profileImage, flexible_color, getActivity());
                } else {
                    Glide.with(Objects.requireNonNull(getActivity())).load(R.drawable.user_image).into(profile_image);
                }


                location.setBackground(null);
                location.setText(userModel.getCountry());
                if (userModel.getUserInfoModel().getJoined_date() != null) {
                    String current_Time = "Joined " + ConvertTimeUtil.fromTimestampToMonth(userModel.getUserInfoModel().getJoined_date().toString());
                    Joined_date.setBackground(null);
                    Joined_date.setText(current_Time);
                }

                flag.setBackground(null);
                Glide.with(Objects.requireNonNull(getActivity())).load(userModel.getUserInfoModel().getCountryFlag()).into(flag);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            DisplayMessageToast.MakeMessage(getContext(), "there is something wrong", Toast.LENGTH_SHORT);
        }

    }
}
