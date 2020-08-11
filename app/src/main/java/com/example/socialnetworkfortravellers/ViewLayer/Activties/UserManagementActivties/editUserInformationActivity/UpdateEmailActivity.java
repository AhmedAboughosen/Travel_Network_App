package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters.IUpdateEmailPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdateEmailPresenters.IUpdateEmailPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.UpdateEmailInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
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

public class UpdateEmailActivity extends BaseActivity {


    @Length(min = 9, max = 20, message = "The password you entered is incorrect")
    @Password(message = "")
    @BindView(R.id.current_password)
    TextInputEditText current_password;

    @Order(0)
    @Email(message = "EmailAddress address is not formatted correctly")
    @BindView(R.id.new_email)
    TextInputEditText new_email;

    @BindView(R.id.current_email)
    TextInputEditText current_email;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    IUpdateEmailPresenter mUpdateEmailPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_email);
        initView();
        setUpInjector();
        setEmail();
        setUpdateEmailPresenterCallBack();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

    }


    private void setUpInjector() {
        UpdateEmailInjector.getSharedInjector().injectIn(this);
    }

    private void setEmail() {
        try {
            current_email.setInputType(InputType.TYPE_NULL);
            current_email.setText(CurrentUserIDUtil.getInstance().getCurrentUser().getEmail());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    private void setUpdateEmailPresenterCallBack() {
        mUpdateEmailPresenter.setUpdateEmailPresenterCallBack(new IUpdateEmailPresenterCallBack() {
            @Override
            public void failure(String message) {
                finishWaiting();
                Toast.makeText(UpdateEmailActivity.this, message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void updateEmailSuccessful() {
                finishWaiting();
                Toast.makeText(UpdateEmailActivity.this, "your Email is Updated Successfully.", Toast.LENGTH_SHORT).show();
                finish();
                animateWithZoom();
            }
        });
    }


    @OnClick(R.id.update_email)
    public void onUpdateEmailButtonClick() {
        startWaiting("Please wait, while we are Updating your new Email..." , false);
        mUpdateEmailPresenter.updateEmail(new_email.getText().toString(), current_password.getText().toString());
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
