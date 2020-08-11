package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.deactivateAccountActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters.IDeactivateAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.deactivateAccountPresenters.IDeactivateAccountPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.DeactivateAccountInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class DeactivateAccountActivity extends BaseActivity {


    @BindView(R.id.profile_image)
    CircleImageView nav_profile_image;
    @BindView(R.id.fullname)
    TextView nav_user_name;

    @Length(min = 9, max = 20, message = "the password you entered is not correct!")
    @Password(message = "")
    @BindView(R.id.confirm_password)
    TextInputEditText confirm_password;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    UserSharedPrefManager sharedPrefManager;
    @Inject
    IDeactivateAccountPresenter mDeactivateAccountPresenter;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deactivate_account);

        initView();
        setUpInject();
        setUserInfo();


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        setDeactivateAccountPresenterCallBack();
    }


    /**
     * setUp Inject
     */
    private void setUpInject() {
        mAuth = FirebaseAuth.getInstance();
        DeactivateAccountInjector.getSharedInjector().injectIn(this);
    }

    /**
     * set User Info Full Name and Profile pic
     */
    private void setUserInfo() {

        try {
            nav_user_name.setText(sharedPrefManager.getFullName());
            if (!StringEmptyUtil.isEmptyString(sharedPrefManager.getImageUrl())) {
                Glide.with(Objects.requireNonNull(getApplicationContext())).load(sharedPrefManager.getImageUrl()).into(nav_profile_image);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }


    private void setDeactivateAccountPresenterCallBack() {
        mDeactivateAccountPresenter.setDeactivateAccountPresenterCallBack(new IDeactivateAccountPresenterCallBack() {
            @Override
            public void failure(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void removeUserDataSuccessful() {
                finishWaiting();
                mAuth.signOut();
                SendUserToLoginActivity();
            }
        });
    }

    private void SendUserToLoginActivity() {
        try {
            Intent loginIntent = new Intent(DeactivateAccountActivity.this, LoginActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
            animateWithZoom();
            finishAffinity();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    @OnClick(R.id.deactive)
    public void onDeleteAccountClick() {
        startWaiting("Please wait, while we are Deactivate your Account", false);
        mDeactivateAccountPresenter.deactivateAccount(confirm_password.getText().toString());
    }

}
