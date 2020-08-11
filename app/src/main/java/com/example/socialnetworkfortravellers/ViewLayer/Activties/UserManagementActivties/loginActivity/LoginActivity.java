package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters.ILoginPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.loginPresenters.ILoginPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.LoginInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.forgetAccountActivity.ForgetAccountActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.RegisterActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities.SetUpActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ISendToActivityUtli;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

    /*
validation section.
 */
    @Order(0)
    @Email(message = "EmailAddress address is not formatted correctly")
    @BindView(R.id.email)
    TextInputEditText mEmail;

    @Length(min = 9, max = 20, message = "The Password you entered is incorrect")
    @Password(message = "")
    @BindView(R.id.password)
    TextInputEditText mPassword;

    @BindView(R.id.loginCard)
    LinearLayout mLoginCardCardView;


    private Animation shakeAnimation;

    @Inject
    ILoginPresenter mLoginPresenter;


    @Inject
    ISendToActivityUtli mSendToActivityUtli;

    @Inject
    UserSharedPrefManager mUserSharedPrefManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.LOGIN);
        setUpInjector();
        setUpViews();
        setUpLoginPresenterCallBack();
    }

    private void setUpInjector() {
        LoginInjector.getSharedInjector().injectIn(this);
    }


    private void setUpViews() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }


    /**
     * onLoginClick
     *
     * @param view
     */
    @OnClick(R.id.sign_in)
    public void onLoginClick(View view) {
        startWaiting("Please wait, while we are allowing you to login into your Account...", false);
        mLoginPresenter.login(mEmail.getText().toString(), mPassword.getText().toString());
    }


    /**
     * onCreateAccountClick
     */
    @OnClick(R.id.create_an_account)
    public void onCreateAccountClick() {
        mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(LoginActivity.this, RegisterActivity.class);
    }

    /**
     * onForgetAccountClick
     */
    @OnClick(R.id.forgot_password)
    public void onForgetAccountClick() {
        mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(LoginActivity.this, ForgetAccountActivity.class);
    }


    /**
     * onForgetAccountClick
     */
    @OnClick(R.id.sign_in_with_google)
    public void signiInWithGoogle() {
        Toast.makeText(this, "in progress...", Toast.LENGTH_SHORT).show();
    }


    /**
     * onForgetAccountClick
     */
    @OnClick(R.id.sign_in_with_facebook)
    public void signiInWithFaceBook() {
        Toast.makeText(this, "in progress...", Toast.LENGTH_SHORT).show();
    }


    /**
     * onForgetAccountClick
     */
    @OnClick(R.id.sign_in_with_twitter)
    public void signiInWithTwitter() {
        Toast.makeText(this, "in progress...", Toast.LENGTH_SHORT).show();
    }


    /**
     * setUpLoginPresenterCallBack
     */
    private void setUpLoginPresenterCallBack() {
        mLoginPresenter.setUpLoginPresenterCallBack(new ILoginPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(LoginActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successCompleteSignInRequest(UserModel mUserModel) {
                finishWaiting();

                if (mUserModel == null || mUserModel.getFullName().isEmpty()) {
                    mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(LoginActivity.this, SetUpActivity.class);
                    return;
                }

                mUserSharedPrefManager.setFullName(mUserModel.getFullName());
                mUserSharedPrefManager.setCountryName(mUserModel.getCountry());
                mUserSharedPrefManager.setImageUrl(mUserModel.getProfilePicture());
                mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(LoginActivity.this, MainActivity.class);

                finish();
                finishAffinity();
            }

            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                mLoginCardCardView.startAnimation(shakeAnimation);
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void thisUserNotExists() {
                finishWaiting();
                mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(LoginActivity.this, SetUpActivity.class);
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.removeEventListener();
    }
}
