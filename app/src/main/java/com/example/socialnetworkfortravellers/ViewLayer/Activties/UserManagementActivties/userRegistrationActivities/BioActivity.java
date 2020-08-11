package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters.IBioPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.bioPresenters.IBioPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.BioInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.AddInterestActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class BioActivity extends BaseActivity {

    @BindView(R.id.count_bio)
    TextView count_bio;


    @Length(min = 9, max = 60, message = "Bio must be 9-60 characters ")
    @BindView(R.id.bio)
    EditText mBioEditText;


    @Inject
    IBioPresenter mBioPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);
        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.BIO);
        initView();
        setUpInject();
        setUpBioPresenterCallBack();
    }

    private void setUpInject() {
        BioInjector.getSharedInjector().injectIn(this);
    }


    @OnClick(R.id.next)
    public void onSaveBio() {
        startWaiting("Please wait, while we updating your Bio...", false);
        mBioPresenter.saveBio(mBioEditText.getText().toString());
    }


    @OnClick(R.id.skip)
    public void onSkipBio() {
        sendUserToAddInterestActivity();
        finishAffinity();
        finish();
    }

    public void setUpBioPresenterCallBack() {

        mBioPresenter.setUpBioPresenterCallBack(new IBioPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(BioActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void saveBioSuccessful(String message) {
                finishWaiting();
                sendUserToAddInterestActivity();
                finish();
                finishAffinity();
            }


            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }
        });
    }


    /**
     * from current activity to BioActivity
     */
    protected void sendUserToAddInterestActivity() {
        SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(this, AddInterestActivity.class);
    }


    @OnTextChanged(R.id.bio)
    public void onBioTextChange(CharSequence charSequence, int i, int i1, int i2) {
        try {
            count_bio.setText(charSequence.length() + "");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        OkCancelDialog okCancelDialog = new OkCancelDialog(this);
        okCancelDialog.show("Are you sure you want to leave this Step?", "if yes, you can write Bio later.", new IOkCancelDialogCallBack() {
            @Override
            public void onCancelClick() {
                okCancelDialog.dismiss();
            }

            @Override
            public void onOkClick() {
                okCancelDialog.dismiss();
                sendUserToAddInterestActivity();
                finishAffinity();
                finish();
            }
        });
    }
}
