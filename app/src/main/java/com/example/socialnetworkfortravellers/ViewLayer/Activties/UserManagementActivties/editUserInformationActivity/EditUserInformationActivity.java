package com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.editUserInformationActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
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

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters.IEditUserInformationPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.UserManagementPresenters.editUserInformationPresenters.IEditUserInformationPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors.EditUserInformationInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.UserSharedPrefManager;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites.EditInterestActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.deactivateAccountActivity.DeactivateAccountActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.ImagePickerActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.CountryAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.utilLayer.FlexibleColorUtli;
import com.example.socialnetworkfortravellers.utilLayer.ParseValidationErrorUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditUserInformationActivity extends ImagePickerActivity {

    public static final String USER_OBJECT = "UserObject";
    public @BindView(R.id.flexible_color)
    LinearLayout flexible_color;
    public @BindView(R.id.profile_image)
    CircleImageView profile_image;

    public @BindView(R.id.update_data)
    Button update_data;
    @Length(min = 10, max = 40, message = "FullName should be more than 10 characters")
    @NotEmpty(message = "")
    public @BindView(R.id.fullname)
    TextInputEditText fullname;
    @Length(min = 9, max = 60, message = "mBio must be 9-60 characters ")
    public @BindView(R.id.Bio)
    TextInputEditText Bio;
    public @BindView(R.id.location)
    Spinner location;
    public @BindView(R.id.birth_day)
    DatePicker birth_day;
    public @BindView(R.id.Email_address)
    Button Email_address;
    public @BindView(R.id.password)
    Button password;
    public @BindView(R.id.interest)
    Button interest;
    public @BindView(R.id.deactive_accout)
    Button deactive_accout;

    public @BindView(R.id.femle)
    RadioButton femle;
    public @BindView(R.id.male)
    RadioButton male;
    public @BindView(R.id.gender)
    RadioGroup gender;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.retry)
    Button mRetryButton;

    @BindView(R.id.download_data)
    LinearLayout mDownloadDataLinearLayout;

    @BindView(R.id.error_load_Country)
    TextView mErrorLoadCountry;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    private String mCountry, mSelectCountryFlag;
    private boolean isGender;
    private String mNewProfileImage = "", mOldProfileImage = "", mOldCountry = "";
    private UserModel mUserModel;

    @Inject
    IEditUserInformationPresenter mEditUserInformationPresenter;
    @Inject
    UserSharedPrefManager mUserSharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_information);

        initView();
        setUpInject();
        getUserModel();
        setUserInitialData();
        setUpEditUserInformationPresenterCallBack();
        setUpEvent();


        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
    }


    private void getUserModel() {
        mUserModel = (UserModel) getIntent().getSerializableExtra(USER_OBJECT);
    }

    private void setUpInject() {
        EditUserInformationInjector.getSharedInjector().injectIn(this);
    }


    @OnClick(R.id.update_data)
    public void onUpdateButtonClick() {
        startWaiting("Please wait, while we are Updating your new Information..." , false);
        String date = birth_day.getDayOfMonth() + "/" + ((birth_day.getMonth() + 1) + "/") +//month is 0 based
                birth_day.getYear();
        mUserModel.setBio(Bio.getText().toString());
        mUserModel.setFullName(fullname.getText().toString());
        mUserModel.setDate_of_birth(date);
        mUserModel.getUserInfoModel().setCountryFlag(mSelectCountryFlag);
        mUserModel.setGender(isGender);
        mUserModel.setCountry(mCountry);
        mUserModel.setProfilePicture(mNewProfileImage);
        mEditUserInformationPresenter.updateUserInfo(mUserModel, !mNewProfileImage.isEmpty() && !mNewProfileImage.equals(mOldProfileImage));
    }

    @OnClick(R.id.profile_image)
    public void onProfileImageClick() {
        this.fileActivity();
    }


    @OnClick(R.id.Email_address)
    public void onUpdateEmailClick() {
        startActivity(new Intent(EditUserInformationActivity.this, UpdateEmailActivity.class));
        animateWithZoom();
    }

    @OnClick(R.id.password)
    public void onUpdatePasswordClick() {
        startActivity(new Intent(EditUserInformationActivity.this, UpdatePasswordActivity.class));
        animateWithZoom();
    }

    @OnClick(R.id.deactive_accout)
    public void onDeactivateAccountClick() {
        startActivity(new Intent(EditUserInformationActivity.this, DeactivateAccountActivity.class));
        animateWithZoom();
    }

    @OnClick(R.id.interest)
    public void onInterestClick() {
        startActivity(new Intent(EditUserInformationActivity.this, EditInterestActivity.class));
        animateWithZoom();
    }

    @OnClick(R.id.retry)
    public void onRetryButtonClick() {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetryButton.setVisibility(View.GONE);
        mErrorLoadCountry.setVisibility(View.GONE);
        mDownloadDataLinearLayout.setVisibility(View.VISIBLE);
        loadCountryInSpinner();
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }

    /**
     * setUpEvent
     */
    private void setUpEvent() {
        gender.setOnCheckedChangeListener((group, checkedId) -> {
                    try {
                        RadioButton radioButton = findViewById(checkedId);
                        isGender = (radioButton.getText().equals("male"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
        );
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
        mEditUserInformationPresenter.getFilePath(requestCode, resultCode, data);
    }


    private void setUpEditUserInformationPresenterCallBack() {

        mEditUserInformationPresenter.setUpEditUserInformationPresenterCallBack(new IEditUserInformationPresenterCallBack() {

            @Override
            public void ExceptionMessage(String message) {
                Toast.makeText(EditUserInformationActivity.this, "there is something wrong, please try later." + "\n " + message, Toast.LENGTH_SHORT).show();

            }

            /**
             * get all Country and set in spinner.
             * @param list
             */
            @Override
            public void getAllCountry(List<CountryModel> list) {

                CountryModel city = new CountryModel();
                city.setCityName(mCountry);
                city.setFlag(mSelectCountryFlag);
                list.add(0, city);
                mProgressBar.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.GONE);
                mDownloadDataLinearLayout.setVisibility(View.GONE);
                setUpAdapter(list);
            }

            @Override
            public void noCountry(String str) {
                Toast.makeText(EditUserInformationActivity.this, str, Toast.LENGTH_SHORT).show();
                mErrorLoadCountry.setVisibility(View.VISIBLE);

                mProgressBar.setVisibility(View.GONE);
                mRetryButton.setVisibility(View.VISIBLE);
                mDownloadDataLinearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void internetIsNotConnected() {
                Toast.makeText(EditUserInformationActivity.this, getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
              //  finish();
            }

            @Override
            public void updateProfileImage(String profileImage) {
                mNewProfileImage = profileImage;
                if (!StringEmptyUtil.isEmptyString(mNewProfileImage)) {
                    Glide.with(Objects.requireNonNull(getApplicationContext())).load(mNewProfileImage).into(profile_image);
                    FlexibleColorUtli.setFlexible_color(mNewProfileImage, flexible_color, getApplicationContext());
                }
            }

            @Override
            public void releasePicker() {
                mNewProfileImage = "";
                if (!StringEmptyUtil.isEmptyString(mOldProfileImage)) {
                    Glide.with(Objects.requireNonNull(getApplicationContext())).load(mOldProfileImage).into(profile_image);
                    FlexibleColorUtli.setFlexible_color(mOldProfileImage, flexible_color, getApplicationContext());
                }
            }

            @Override
            public void errorInValidator(List<ValidationError> errors) {
                finishWaiting();
                ParseValidationErrorUtil.parseValidationError(errors, getApplicationContext());
            }

            @Override
            public void completeSaveDataSuccessfully(String url) {
                finishWaiting();
                setFullNameSharedPrefManager();
                setCountryNameSharedPrefManager();
                if (!url.isEmpty())
                    SaveImageInSharedPre(url);
                Toast.makeText(EditUserInformationActivity.this, "your information is updated Successfully.", Toast.LENGTH_SHORT).show();
                finish();
                animateWithZoom();
            }

        });

    }


    private void setUserInitialData() {

        try {
            this.fullname.setText(mUserModel.getFullName());
            this.Bio.setText(mUserModel.getBio());

            if (!StringEmptyUtil.isEmptyString(mUserModel.getProfilePicture())) {
                mOldProfileImage = mUserModel.getProfilePicture();
                Glide.with(Objects.requireNonNull(getApplicationContext())).load(mUserModel.getProfilePicture()).into(profile_image);
                FlexibleColorUtli.setFlexible_color(mUserModel.getProfilePicture(), flexible_color, getApplicationContext());
            }

            isGender = (mUserModel.getGender());
            ((isGender) ? male : femle).setChecked(true);

            setBirthDay(mUserModel.getDate_of_birth());
            mCountry = mUserModel.getCountry();
            mOldCountry = mUserModel.getCountry();
            mSelectCountryFlag = mUserModel.getUserInfoModel().getCountryFlag();
            loadCountryInSpinner();
        } catch (Exception ex) {
            ex.printStackTrace();
            Toast.makeText(this, "there is something wrong, please try later.", Toast.LENGTH_SHORT).show();

        }

    }


    /**
     * this method used to parse date and set date in datePicker
     *
     * @param birthDay
     */
    public void setBirthDay(String birthDay) {

        Date readDate = null;
        try {
            DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

            readDate = df.parse(birthDay);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(readDate.getTime());

            //convert them to int
            int mDay = cal.get(Calendar.DAY_OF_MONTH);
            int mMonth = cal.get(Calendar.MONTH);
            int mYear = cal.get(Calendar.YEAR);

            birth_day.updateDate(mYear, mMonth, mDay);

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "there is something wrong, please try later.", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * get all country list then display it in spinner.
     */
    private void loadCountryInSpinner() {
        mEditUserInformationPresenter.getAllCountry(mOldCountry);
    }


    /**
     * set Adapter
     *
     * @param list
     */
    public void setUpAdapter(List<CountryModel> list) {
        CountryAdapter CountryAdapter = new CountryAdapter(EditUserInformationActivity.this, list);
        this.location.setAdapter(CountryAdapter);


        this.location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCountry = list.get(position).getCityName();
                mSelectCountryFlag = list.get(position).getFlag();
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
        mUserSharedPrefManager.setFullName(Objects.requireNonNull(fullname.getText()).toString());
    }


    /**
     * save country name of User in shared Pre.
     */
    protected void setCountryNameSharedPrefManager() {
        mUserSharedPrefManager.setCountryName(mCountry);
    }


    /**
     * Save User Image in file shared Pre
     *
     * @param uri
     */
    private void SaveImageInSharedPre(String uri) {
        mUserSharedPrefManager.setImageUrl(uri);
    }
}
