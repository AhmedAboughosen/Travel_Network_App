package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.forgetAccountActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters.IForgetAccountPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.forgetAccountPresenters.IForgetAccountPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.ForgetAccountInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Email;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ForgetAccountActivity extends BaseActivity {


    @Email(message = "EmailAddress address is not formatted correctly")
    @BindView(R.id.email)
    TextInputEditText mEmailEditText;


    @Inject
    IForgetAccountPresenter mForgetAccountPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_account);
        setUpInject();
        initView();
        setUpForgetAccountPresenterCallBack();
    }


    private void setUpInject() {
        ForgetAccountInjector.getSharedInjector().injectIn(this);
    }


    @OnClick(R.id.check)
    public void onCheckEmailClick() {
        startWaiting("Please wait, while we are Sending Password to  your Email", false);
        mForgetAccountPresenter.sendPasswordResetEmail(mEmailEditText.getText().toString());
    }


    private void setUpForgetAccountPresenterCallBack() {
        mForgetAccountPresenter.setUpForgetAccountPresenterCallBack(new IForgetAccountPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                showMessagesError(getString(R.string.no_internet));
            }

            @Override
            public void sendPasswordSuccessful() {
                finishWaiting();
                Intent intent = new Intent(ForgetAccountActivity.this, CheckEmailActivity.class);
                intent.putExtra(CheckEmailActivity.EMAIL, mEmailEditText.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }

            @Override
            public void onValidationFailed(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
