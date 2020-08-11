package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.userRegistrationActivities;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters.ISetUpPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.SetUpPresenters.ISetUpPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.SetUpInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.loginActivity.LoginActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.CountryAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ISendToActivityUtli;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.ServerValue;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class SetUpActivity extends BaseActivity {


    /*
    validation section.
     */
    @Length(min = 10, max = 40, message = "FullName should be more than 10 characters")
    @NotEmpty(message = "")
    @BindView(R.id.full_name)
    TextInputEditText mFullnameEditText;

    @Order(0)
    @Select(message = "Please Select Your Your country !")
    @BindView(R.id.country)
    Spinner mCountrySpinner;


    @Order(1)
    @Checked(message = "Please Select Your Gender !")
    @BindView(R.id.gender)
    RadioGroup mGenderRadioGroup;


    @BindView(R.id.birth_day)
    DatePicker mBirthDayDatePicker;


    @BindView(R.id.error_load_Country)
    TextView mErrorLoadCountry;


    @BindView(R.id.loginCard)
    LinearLayout mLoginCardCardView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.retry)
    Button mRetryButton;


    @Inject
    ISetUpPresenter mSetUpPresenter;

    @Inject
    ISendToActivityUtli mSendToActivityUtli;

    @Inject
    UserSharedPrefManager mUserSharedPrefManager;

    @Inject
    UserModel mUserModel;

    private String mCountry = "", mSelectCountryFlag = "";
    private boolean isGender;
    private Animation shakeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);
        initView();
        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.SET_UP);
        setOnCheckedChangeListener();
        setUpInjector();
        setUpViews();
        loadCountryInSpinner();
        setUpSetUpPresenterCallBack();
    }


    @OnClick(R.id.set_up_button)
    public void clickOnSetUpButton(View view) {
        startWaiting("Please wait, while we are creating your new Account...", false);
        String date = mBirthDayDatePicker.getDayOfMonth() + "/" + ((mBirthDayDatePicker.getMonth() + 1) + "/") +//month is 0 based
                mBirthDayDatePicker.getYear();
        mUserModel.setDate_of_birth(date);
        mUserModel.setFullName(mFullnameEditText.getText().toString());
        mUserModel.getUserInfoModel().setJoined_date(ServerValue.TIMESTAMP);
        mSetUpPresenter.setUpNewAccount(mUserModel);
    }


    /**
     * setUpInjector
     */
    private void setUpInjector() {
        SetUpInjector.getSharedInjector().injectIn(this);
    }


    /**
     * setUpEvent
     */
    private void setOnCheckedChangeListener() {
        mGenderRadioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                    try {
                        RadioButton radioButton = findViewById(checkedId);
                        isGender = (radioButton.getText().equals("male"));
                        mUserModel.setGender(isGender);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
    }


    private void setUpViews() {
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake);
    }


    /**
     * setUpSetUpPresenterCallBack
     */
    private void setUpSetUpPresenterCallBack() {
        mSetUpPresenter.setUpSetUpPresenterCallBack(new ISetUpPresenterCallBack() {
            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                mLoginCardCardView.startAnimation(shakeAnimation);
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void completeSaveDataSuccessfully() {
                finishWaiting();
                setFullNameSharedPrefManager();
                setCountryNameSharedPrefManager();
                Toast.makeText(SetUpActivity.this, "your Account is created Successfully.", Toast.LENGTH_LONG).show();
                mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(SetUpActivity.this, ProfilePictureActivity.class);
                finish();
                finishAffinity();
            }

            @Override
            public void showMessageError(String message) {
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                Toast.makeText(SetUpActivity.this, getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }

            /**
             * get all Country and set in spinner.
             * @param list
             */
            @Override
            public void getAllCountry(List<CountryModel> list) {
                CountryModel city = new CountryModel();
                city.setCityName("Select Country");
                city.setFlag(" ");
                list.add(0, city);
                setUpAdapter(list);
                mProgressBar.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.GONE);
            }

            @Override
            public void noCountry(String str) {
                mErrorLoadCountry.setVisibility(View.VISIBLE);
                mProgressBar.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.VISIBLE);
                CountryModel city = new CountryModel();
                city.setCityName("Select Country");
                city.setFlag(" ");
                setUpAdapter(Arrays.asList(city));
            }
        });
    }


    /**
     * get all country list then display it in spinner.
     */
    private void loadCountryInSpinner() {
        mSetUpPresenter.getAllCountry();
    }


    @OnClick(R.id.retry)
    public void onRetryButtonClick() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
        mErrorLoadCountry.setVisibility(View.GONE);
        mSetUpPresenter.getAllCountry();
    }

    /**
     * set Adapter
     *
     * @param list
     */
    public void setUpAdapter(List<CountryModel> list) {
        CountryAdapter CountryAdapter = new CountryAdapter(SetUpActivity.this, list);
        mCountrySpinner.setAdapter(CountryAdapter);

        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountry = list.get(position).getCityName();
                mSelectCountryFlag = list.get(position).getFlag();
                mUserModel.setCountry(mCountry);
                mUserModel.getUserInfoModel().setCountryFlag(mSelectCountryFlag);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }


    /**
     * save Full Name of User in shared Pre
     */
    private void setFullNameSharedPrefManager() {
        mUserSharedPrefManager.setFullName(mFullnameEditText.getText().toString());
    }


    /**
     * save country name of User in shared Pre.
     */
    protected void setCountryNameSharedPrefManager() {
        mUserSharedPrefManager.setCountryName(mCountry);
    }


    @Override
    public void onBackPressed() {
        OkCancelDialog okCancelDialog = new OkCancelDialog(this);
        okCancelDialog.show("Are you sure you want to leave this step?", "if yes, you can continue create your account once you login in.", new IOkCancelDialogCallBack() {
            @Override
            public void onCancelClick() {
                okCancelDialog.dismiss();
            }

            @Override
            public void onOkClick() {
                okCancelDialog.dismiss();
                mUserSharedPrefManager.setCountryName("");
                mUserSharedPrefManager.setFullName("");
                mSendToActivityUtli.SendUserToOtherActivityWithTransitionLeftin_out(SetUpActivity.this, LoginActivity.class);
                finish();
                finishAffinity();
            }
        });
    }
}
