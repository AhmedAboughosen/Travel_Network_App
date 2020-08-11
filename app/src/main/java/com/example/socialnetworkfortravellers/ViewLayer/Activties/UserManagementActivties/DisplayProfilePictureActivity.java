package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IFlexibleColorBack;
import com.example.socialnetworkfortravellers.utilLayer.FlexibleColorUtli;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisplayProfilePictureActivity extends BaseActivity {

    public static final String PROFILE_IMAGE = "ProfileImage";

    @BindView(R.id.profile_image)
    ImageView profile_image;
    @BindView(R.id.flexible_color)
    LinearLayout flexible_color;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_profile_picture);
        initView();
        intiUI();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Profile Picture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        if (navIcon != null) {
            navIcon.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }
    }

    private void intiUI() {
        try {
            ButterKnife.bind(this);
            String Profile_Image = getIntent().getStringExtra(PROFILE_IMAGE);
            setProfileImage(Profile_Image);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * this function used to populate Image of user In Profile Image
     */

    private void setProfileImage(String s) {
        if (!StringEmptyUtil.isEmptyString(s)) {
            FlexibleColorUtli.setFlexible_color(s, flexible_color, getApplicationContext());
            Glide.with(Objects.requireNonNull(getApplicationContext())).load(s).into(profile_image);
        } else {
            profile_image.setImageResource(R.drawable.user_image);
        }
    }
}