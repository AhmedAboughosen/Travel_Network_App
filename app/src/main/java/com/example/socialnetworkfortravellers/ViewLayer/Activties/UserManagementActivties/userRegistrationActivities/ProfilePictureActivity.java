package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters.IProfilePicturePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.ProfilePicturePresenters.IProfilePicturePresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.ProfilePictureInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;

import java.io.File;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePictureActivity extends ImagePickerActivity {


    public @BindView(R.id.image_layout)
    LinearLayout mImageLayoutLinearLayout;


    private String imageUrl;


    @Inject
    IProfilePicturePresenter mProfilePicturePresenter;
    @Inject
    UserSharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_picture);

        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.PROFILE_PICTURE);
        setUpInjector();
        initView();
        setUpAddImageLayout();
        setUpProfilePicturePresenterCallBack();
    }


    private void setUpInjector() {
        ProfilePictureInjector.getSharedInjector().injectIn(this);
    }


    @OnClick(R.id.next)
    public void saveImageFile(View view) {
        startWaiting("Please wait, while we Upload your profile image...", false);
        mProfilePicturePresenter.saveProfileImage(imageUrl);
    }


    @OnClick(R.id.skip)
    public void onClickSkip(View view) {
        sharedPrefManager.setImageUrl("");
        finish();
        finishAffinity();
        sendUserToBioActivity();
    }

    /**
     * create Image view with Background Image and Resource Image and set click Listener then add new view into flexible layout
     */
    public void setUpAddImageLayout() {
        try {
            final ImageView imageView = new ImageView(this);


            /*
            style of Image View
             */
            imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // value is in pixels
            imageView.setBackgroundResource(R.drawable.dotted);
            imageView.setImageResource(R.drawable.ic_image_add_button);
            imageView.setPadding(12, 12, 12, 12);

            /*
            when user click on Image View
             */
            imageView.setOnClickListener(view -> this.fileActivity());


            // Add ImageView to LinearLayout
            if (this.mImageLayoutLinearLayout != null) {
                this.mImageLayoutLinearLayout.removeAllViews();
                this.mImageLayoutLinearLayout.addView(imageView);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    /**
     * create Circle Image view with Profile Image  and set click Listener then add new view into flexible layout
     *
     * @param img
     */
    protected void setUpCircleImage(String img) {
        try {
            final CircleImageView imageView = new CircleImageView(this);

            //   style of Image View

            imageView.setLayoutParams(new LinearLayout.LayoutParams(200, 200)); // value is in pixels
            imageView.setPadding(0, 0, 0, 0);

            Glide.with(Objects.requireNonNull(this))
                    .load(new File(img))
                    .into(imageView);

            imageView.setOnClickListener(view -> this.fileActivity());

            // Add ImageView to LinearLayout
            if (this.mImageLayoutLinearLayout != null) {
                this.mImageLayoutLinearLayout.removeAllViews();
                this.mImageLayoutLinearLayout.addView(imageView);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

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
        mProfilePicturePresenter.getFilePath(requestCode, resultCode, data);
    }


    public void setUpProfilePicturePresenterCallBack() {
        mProfilePicturePresenter.setProfilePicturePresenterCallBack(new IProfilePicturePresenterCallBack() {

            @Override
            public void showMessageError(String message) {

                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(ProfilePictureActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }


            @Override
            public void saveImageUrl(String url, String message) {
                finishWaiting();
                Toast.makeText(ProfilePictureActivity.this, message, Toast.LENGTH_SHORT).show();
                SaveImageInSharedPre(url);
                finish();
                finishAffinity();
                sendUserToBioActivity();
            }

            @Override
            public void setUpCircleImage(String url) {
                imageUrl = url;
                ProfilePictureActivity.this.setUpCircleImage(imageUrl);
            }

            @Override
            public void setUpAddImageLayout() {
                ProfilePictureActivity.this.setUpAddImageLayout();
                imageUrl = "";
            }
        });
    }


    /**
     * from current activity to BioActivity
     */
    protected void sendUserToBioActivity() {
        SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(this, BioActivity.class);
    }


    /**
     * Save User Image in file shared Pre
     *
     * @param uri
     */
    private void SaveImageInSharedPre(String uri) {
        sharedPrefManager.setImageUrl(uri);
    }


    @Override
    public void onBackPressed() {
        OkCancelDialog okCancelDialog = new OkCancelDialog(this);
        okCancelDialog.show("Are you sure you want to leave this step?", "if yes, you can upload your Profile Image later.", new IOkCancelDialogCallBack() {
            @Override
            public void onCancelClick() {
                okCancelDialog.dismiss();
            }

            @Override
            public void onOkClick() {
                okCancelDialog.dismiss();
                sharedPrefManager.setImageUrl("");
                finish();
                finishAffinity();
                sendUserToBioActivity();
            }
        });
    }
}
