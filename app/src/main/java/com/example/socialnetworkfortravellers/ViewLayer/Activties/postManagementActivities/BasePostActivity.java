package com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.PostModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters.IBasePostPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.PostManagementPresenters.basePostPresenters.IBasePostPresenterCallBack;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.SelectedImageAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.EnterLocationDialog;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public abstract class BasePostActivity extends ImagePickerActivity {

    @BindView(R.id.content_layout)
    LinearLayout content_layout;
    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    //location view
    public @BindView(R.id.location_view)
    ImageView location_view;
    public @BindView(R.id.current_location)
    TextView current_location;
    public @BindView(R.id.drop_location)
    ImageView drop_location;

    @BindView(R.id.profile_image)
    CircleImageView profile_image;
    @BindView(R.id.details)
    EditText details;
    @BindView(R.id.share_post)
    Button mEditTextSharePost;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.toolbar_title)
    public  TextView mToolbarTitle;


    @Inject
    UserSharedPrefManager sharedPrefManager;
    @Inject
    SelectedImageAdapter mSelectedImageAdapter;
    @Inject
    PostModel mPostModel;
    @Inject
    UserModel mUserModel;
    @Inject
    IBasePostPresenter mBasePostPresenter;
    @Inject
    EnterLocationDialog mEnterLocationDialog;


    private AutoCompleteTextView mAutoCompleteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_post);
        try {
            initView();
            getUserProfileImage();
            setUpIBasePostPresenterCallBack();
            removeImageItem();

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    @OnClick(R.id.select_image)
    public void onSelectImage() {
        setPhotoPaths(mSelectedImageAdapter.getImages());
        super.COUNT = 4;
        fileActivity();
    }


    @OnClick(R.id.add_location)
    public void onAddLocation() {
        mProgressBar.setVisibility(View.VISIBLE);
        mBasePostPresenter.getAllCountry();
    }

    @OnClick(R.id.drop_location)
    public void onDropLocation() {
        removeLocation();
    }


    /**
     * onActivityResult
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mBasePostPresenter.getFilePath(requestCode, resultCode, data);
    }


    /**
     * setUpAddPostPresenterCallBack
     */
    private void setUpIBasePostPresenterCallBack() {
        mBasePostPresenter.setUpIBasePostPresenterCallBack(new IBasePostPresenterCallBack() {
            @Override
            public void setUpImageUrl(List<String> url) {
                mPostModel.setImageUrl(new ArrayList<>(url));
                mSelectedImageAdapter.updateItems(mPostModel.getImageUrl());
                setUpListOfImageRecyclerView();
            }

            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void getAllCountry(List<CountryModel> list) {
                mProgressBar.setVisibility(View.GONE);
                displayLocationDialog(list);
            }

            @Override
            public void noCountry(String str) {
                mProgressBar.setVisibility(View.GONE);
                Toast.makeText(BasePostActivity.this, "\n we can't open user interface cuz there some problem with your internet or our service.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * display selected Images to user.
     */
    protected void setUpListOfImageRecyclerView() {
        try {

            content_layout.removeAllViews();
            RecyclerView recyclerView = new RecyclerView(getApplicationContext());
            recyclerView.setLayoutParams(
                    new ViewGroup.LayoutParams(
                            // or ViewGroup.LayoutParams.WRAP_CONTENT
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            // or ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.MATCH_PARENT));

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

            // set main_logo LinearLayoutManager with HORIZONTAL orientation
            recyclerView.setLayoutManager(layoutManager);

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(mSelectedImageAdapter); // set the Adapter to RecyclerView


            content_layout.addView(recyclerView);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * when user remove Image
     */
    public void removeImageItem() {

        mSelectedImageAdapter.setUpRemoveImageItem(this::removeImage);
    }


    protected void removeImage(int position) {
        ArrayList<String> list = mSelectedImageAdapter.getImages();
        list.remove(position);
        mPostModel.setImageUrl(list);

        mSelectedImageAdapter.updateItems(new ArrayList<>(list));
        mSelectedImageAdapter.notifyDataSetChanged();
    }

    /**
     * if user click on Button Delete
     */
    private void removeLocation() {
        try {
            this.location_view.setVisibility(View.GONE);
            this.current_location.setVisibility(View.GONE);
            this.drop_location.setVisibility(View.GONE);
            this.mPostModel.setLocationPost("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * displayLocationDialog
     *
     * @param list
     */
    private void displayLocationDialog(List<CountryModel> list) {
        Dialog mDialog = mEnterLocationDialog.enterLocationDialog();

        mAutoCompleteTextView = mDialog.findViewById(R.id.enter_location);


        Button ok_button = mDialog.findViewById(R.id.ok);
        ok_button.setOnClickListener(view12 -> {
            try {
                boolean isSelect = setLocationInformation();

                if (isSelect) mDialog.dismiss();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Button cancel_button = mDialog.findViewById(R.id.cancel);
        cancel_button.setOnClickListener(view1 -> mDialog.dismiss());

        ArrayList<String> cityItemList = new ArrayList<>();

          /*
        convert country Item into array of string
         */
        for (int i = 0; i < list.size(); ++i) {
            cityItemList.add(list.get(i).getCityName());
        }

        ArrayAdapter CountryAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cityItemList);
        mAutoCompleteTextView.setAdapter(CountryAdapter);
    }


    /**
     * when user click on Ok Button
     */
    private boolean setLocationInformation() {
        try {
            this.mPostModel.setLocationPost(mAutoCompleteTextView.getText().toString());

            if (StringEmptyUtil.isEmptyString(this.mPostModel.getLocationPost())) {
                Toast.makeText(getApplicationContext(), "Please Select Location", Toast.LENGTH_LONG).show();
                return false;
            }

            this.drop_location.setVisibility(View.VISIBLE);
            this.location_view.setVisibility(View.VISIBLE);
            this.current_location.setVisibility(View.VISIBLE);
            this.current_location.setText(this.mPostModel.getLocationPost());
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), "Please Select Location", Toast.LENGTH_LONG).show();

        return false;
    }


    /**
     * get user profile image from Fire base database then load into profile_image
     */
    private void getUserProfileImage() {
        if (!StringEmptyUtil.isEmptyString(sharedPrefManager.getImageUrl())) {
            Glide.with(Objects.requireNonNull(getApplicationContext())).load(sharedPrefManager.getImageUrl()).into(profile_image);
        } else {
            Glide.with(Objects.requireNonNull(getApplicationContext())).load(R.drawable.user_image).into(profile_image);

        }
    }

}
