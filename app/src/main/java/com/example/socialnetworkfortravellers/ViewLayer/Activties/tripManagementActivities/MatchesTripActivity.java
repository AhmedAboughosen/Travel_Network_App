package com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters.IGetRelatedTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getRelatedTripPresenters.IGetRelatedTripPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors.RelatedTripInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.displayListOfUserActivity.DisplayListOfUserActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class MatchesTripActivity extends BaseActivity {


    public static final String FROMTRIP = "FROMTRIP";
    public static final String COUNTRY_TRIP = "CountryTrip";
    public static final String TOTRIP = "TOTRIP";
    public static final String THERE_IS_NO_TRAVELLERS_WILL_TRAVEL_ON_THESE_DURATION = "there is no travellers will travel on these duration";
    @BindView(R.id.lottieAnimationView)
    LottieAnimationView mLottieAnimationView;
    @BindView(R.id.result)
    TextView mResultTextView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Inject
    IGetRelatedTripPresenter mGetRelatedTripPresenter;

    private String from, to, countryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches_trip);
        initView();
        mLottieAnimationView.setAnimation(R.raw.search);

        setUpInject();
        from = getIntent().getStringExtra(FROMTRIP);
        to = getIntent().getStringExtra(TOTRIP);
        countryName = getIntent().getStringExtra(COUNTRY_TRIP);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

        setUpGetRelatedTripPresenterCallBack();
        getRelatedTrip();
    }


    private void setUpInject() {
        RelatedTripInjector.getSharedInjector().injectIn(this);
    }

    private void getRelatedTrip() {
        mGetRelatedTripPresenter.getRelatedTrip(from, to, countryName);
    }

    private void setUpGetRelatedTripPresenterCallBack() {
        mGetRelatedTripPresenter.setUpGetRelatedTripPresenterCallBack(new IGetRelatedTripPresenterCallBack() {
            @Override
            public void noRelatedTrips() {
                mResultTextView.setText(THERE_IS_NO_TRAVELLERS_WILL_TRAVEL_ON_THESE_DURATION);
                mLottieAnimationView.setAnimation(R.raw.sad_search);
            }

            @Override
            public void getRelatedUserSuccessful(List<String> list_users) {
                Intent intent = new Intent(getApplicationContext(), DisplayListOfUserActivity.class);
                intent.putStringArrayListExtra(DisplayListOfUserActivity.LIST_OF_KEYS, new ArrayList<>(list_users));
                intent.putExtra(DisplayListOfUserActivity.TITLE, "Travelers");
                Toast.makeText(MatchesTripActivity.this, "Found " + list_users.size() + " Travelers", Toast.LENGTH_SHORT).show();
                startActivity(intent);
                finish();
                animateWithZoom();
            }

            @Override
            public void showMessageError(String message) {
                mResultTextView.setText(message);
                mLottieAnimationView.setAnimation(R.raw.no_item);
            }

            @Override
            public void networkErrorMessage() {
                mResultTextView.setText(getString(R.string.no_internet));
                mLottieAnimationView.setAnimation(R.raw.no_internet);
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
