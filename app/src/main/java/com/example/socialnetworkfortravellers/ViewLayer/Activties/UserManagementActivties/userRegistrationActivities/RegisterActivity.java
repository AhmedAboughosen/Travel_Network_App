package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.AccountModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter.IRegisterPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.RegistrationPresenter.IRegisterPresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.RegisterInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ISendToActivityUtli;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * responsibility of this this class is allow user create a new account by use Email and Password.
 */
public class RegisterActivity extends BaseActivity {

    /**
     * instance of Register Activity
     */


    /*
    validation section.
     */
    @Order(0)
    @Email(message = "EmailAddress address is not formatted correctly")
    @BindView(R.id.email)
    TextInputEditText mEmail;

    @Length(min = 9, max = 20, message = "password must be 9-20 characters and include numbers and both upper and lower case letters")
    @Password(message = "")
    @BindView(R.id.password)
    TextInputEditText mPassword;

    @ConfirmPassword(message = "password and confirm password must match")
    @BindView(R.id.confirm_password)
    TextInputEditText mConfirmPassword;

    @BindView(R.id.loginCard)
    LinearLayout mLoginCardCardView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    IRegisterPresenter mRegisterPresenter;

    @Inject
    UserSharedPrefManager mUserSharedPrefManager;

    private Animation shakeAnimation;

    @Inject
    ISendToActivityUtli mSendToActivityUtli;

    @Inject
    AccountModel mAccountModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setUpInject();
        initView();
        setUpViews();
        setUpRegisterPresenterCallback();
    }


    private void setUpViews() {
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }


    /**
     * onSignUpClick
     */
    @OnClick(R.id.sign_up)
    public void onSignUpClick() {
        mAccountModel.setEmail(mEmail.getText().toString());
        mAccountModel.setPassword(mPassword.getText().toString());
        startWaiting("Please wait, while we are creating your new Account...", false);
        mRegisterPresenter.createNewAccount(mAccountModel);
    }


    /**
     * onSignInClick
     */
    @OnClick(R.id.sign_in)
    public void onSignInClick() {
        finish();
        animateWithZoom();
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }

    private void setUpRegisterPresenterCallback() {
        mRegisterPresenter.setUpRegisterPresenterCallback(new IRegisterPresenterCallback() {
            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(RegisterActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void successCompleteSignInRequest() {
                finishWaiting();
                mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(RegisterActivity.this, SetUpActivity.class);
                finish();
                finishAffinity();
            }


            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                mLoginCardCardView.startAnimation(shakeAnimation);
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

        });
    }

    private void setUpInject() {
        RegisterInjector.getSharedInjector().injectIn(this);
    }

}
