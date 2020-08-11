package com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.ICountriesListPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.ICountriesListPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors.CountryListInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.CountryAdapter;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class FriendTripActivity extends BaseActivity {

    @BindView(R.id.error_load_Country)
    TextView mErrorLoadCountry;
    @BindView(R.id.startDateID)
    DatePicker mStartDatePicker;
    @BindView(R.id.endDateID)
    DatePicker mEndDatePicker;
    @BindView(R.id.country)
    Spinner mCountrySpinner;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Inject
    ICountriesListPresenter mCountriesListPresenter;

    private String mCountry = "";
    private int pos = 0;
    private String from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_trip);

        setUpInjector();
        initView();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);
        loadCountryInSpinner();
        setUpCountriesListPresenterCallBack();
    }

    private void setUpInjector() {
        CountryListInjector.getSharedInjector().injectIn(this);
    }

    /**
     * get all country list then display it in spinner.
     */
    private void loadCountryInSpinner() {
        mCountriesListPresenter.getALLCountries();
    }


    private void setUpCountriesListPresenterCallBack() {
        mCountriesListPresenter.setUpCountriesListPresenterCallBack(new ICountriesListPresenterCallBack() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                CountryModel city = new CountryModel();
                city.setCityName("Select Country");
                city.setFlag(" ");
                list.add(0, city);
                setUpAdapter(list);
            }

            @Override
            public void noCountry(String str) {
                mErrorLoadCountry.setVisibility(View.VISIBLE);
                CountryModel city = new CountryModel();
                city.setCityName("Select Country");
                city.setFlag(" ");
                setUpAdapter(Arrays.asList(city));
            }
        });
    }

    /**
     * set Adapter
     *
     * @param list
     */
    public void setUpAdapter(List<CountryModel> list) {
        CountryAdapter CountryAdapter = new CountryAdapter(FriendTripActivity.this, list);
        mCountrySpinner.setAdapter(CountryAdapter);

        mCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                mCountry = list.get(position).getCityName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @OnClick(R.id.find_buddies)
    public void onFindButtonClick() {

        if (pos == 0) {
            Toast.makeText(this, "Please Select   country !", Toast.LENGTH_SHORT).show();
            return;
        }

        if (checkIfDateCorrect()) {
            Intent intent = new Intent(getApplicationContext(), MatchesTripActivity.class);
            intent.putExtra(MatchesTripActivity.FROMTRIP, from);
            intent.putExtra(MatchesTripActivity.TOTRIP, to);
            intent.putExtra(MatchesTripActivity.COUNTRY_TRIP, mCountry);
            startActivity(intent);
            animateWithZoom();
        } else {
            Toast.makeText(this, "please select correct date.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkIfDateCorrect() {
        from = mStartDatePicker.getYear() + "-" + (mStartDatePicker.getMonth() + 1) + "-" + mStartDatePicker.getDayOfMonth();
        to = mEndDatePicker.getYear() + "-" + (mEndDatePicker.getMonth() + 1) + "-" + mEndDatePicker.getDayOfMonth();
        Calendar fromDate = Calendar.getInstance();
        fromDate.set(mStartDatePicker.getYear(), (mStartDatePicker.getMonth() + 1), mStartDatePicker.getDayOfMonth());
        Calendar toDate = Calendar.getInstance();
        toDate.set(mEndDatePicker.getYear(), (mEndDatePicker.getMonth() + 1), mEndDatePicker.getDayOfMonth());

        int state = fromDate.compareTo(toDate);
        return (state < 0);
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}