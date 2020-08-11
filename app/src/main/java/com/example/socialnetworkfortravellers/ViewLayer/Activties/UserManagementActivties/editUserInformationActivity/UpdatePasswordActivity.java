package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters.IUpdatePassswordPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.UpdatePasswordPresenters.IUpdatePassswordPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.UpdatePasswordInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Password;
import com.subhrajyoti.passwordview.PasswordView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class UpdatePasswordActivity extends BaseActivity {


    @Length(min = 9, max = 20, message = "the password you entered is not correct!")
    @BindView(R.id.current_password)
    TextInputEditText current_password;

    @Length(min = 9, max = 20, message = "password must be 9-20 characters and include numbers and both upper and lower case letters")
    @Password(message = "")
    @BindView(R.id.new_password)
    TextInputEditText new_password;

    @ConfirmPassword(message = "password and confirm password must match")
    @BindView(R.id.confirm_password)
    TextInputEditText confirm_password;


    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    IUpdatePassswordPresenter mUpdatePassswordPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        try {
            initView();
            setUpInector();
            setUpdateEmailPresenterCallBack();


            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setUpInector() {
        UpdatePasswordInjector.getSharedInjector().injectIn(this);
    }

    private void setUpdateEmailPresenterCallBack() {
        mUpdatePassswordPresenter.setUpdatePasswordPresenterCallBack(new IUpdatePassswordPresenterCallBack() {
            @Override
            public void failure(String message) {
                finishWaiting();
                Toast.makeText(UpdatePasswordActivity.this, message, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void updatePasswordSuccessful() {
                finishWaiting();
                Toast.makeText(UpdatePasswordActivity.this, "your Password is Updated Successfully.", Toast.LENGTH_SHORT).show();
                finish();
                animateWithZoom();
            }
        });
    }


    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }

    @OnClick(R.id.update_password)
    public void onUpdatePasswordButtonClick() {
        startWaiting("Please wait, while we updating your Password...", false);
        mUpdatePassswordPresenter.updatePassword(current_password.getText().toString(), new_password.getText().toString());
    }
}
