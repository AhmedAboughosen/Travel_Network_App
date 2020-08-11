package com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters.IAddTripPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.addTripPresenters.IAddTripPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors.AddTripInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.CountryListFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripDateFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments.TripSummaryFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IAddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class AddTripActivity extends BaseActivity implements IAddTripActivity {

    public @BindView(R.id.stateProgressBar)
    StateProgressBar mStateProgressBar;
    public @BindView(R.id.homeToolbar)
    RelativeLayout mHomeToolbar;

    private View toolBarView;

    @Inject
    TripModel tripModel;

    @Inject
    IAddTripPresenter mAddTripPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);

        try {
            initView();
            setUpInject();
            setUpToolbar();
            setUpCountriesListFragment();
            setUpAddTripPresenterCallBack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void setUpInject() {
        AddTripInjector.getSharedInjector().injectIn(this);
    }

    private void setUpCountriesListFragment() {
        UpdateFragmentUtil.loadFragment(new CountryListFragment(), getSupportFragmentManager(), R.id.trip_container);
    }

    private void setUpToolbar() {
        toolBarView = this.getLayoutInflater().inflate(R.layout.snippet_layout_tootlbar_add_trip_fragment, null);
    }


    /**
     *
     */
    private void setUpStepView(StateProgressBar.StateNumber pos) {
        try {
            String[] descriptionData = {"Destination", "Date", "Summary"};
            mStateProgressBar.setStateDescriptionData(descriptionData);
            mStateProgressBar.setCurrentStateNumber(pos);
            mStateProgressBar.enableAnimationToCurrentState(true);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * update toolbar section
     *
     * @param
     */

    public void updateToolbar(View view) {
        this.mHomeToolbar.removeAllViews();
        this.mHomeToolbar.addView(view);
    }


    @Override
    public void updateView(String title, StateProgressBar.StateNumber pos) {
        setUpStepView(pos);
        TextView titleTextView = (TextView) toolBarView.findViewById(R.id.title_text_view);
        ImageView closeImageView = (ImageView) toolBarView.findViewById(R.id.close_dialog);
        closeImageView.setOnClickListener(v -> {
            OkCancelDialog okCancelDialog = new OkCancelDialog(this);
            okCancelDialog.show("Are you sure you want to leave this step?", "", new IOkCancelDialogCallBack() {
                @Override
                public void onCancelClick() {
                    okCancelDialog.dismiss();
                }

                @Override
                public void onOkClick() {
                    okCancelDialog.dismiss();
                    finish();
                    animateWithZoom();
                }
            });
        });

        titleTextView.setText(title);
        updateToolbar(toolBarView);
    }


    @Override
    public void setUpDateFragment(String CountryName) {
        tripModel.setCountryName(CountryName);
        UpdateFragmentUtil.loadFragment(new TripDateFragment(), getSupportFragmentManager(), R.id.trip_container);
    }

    @Override
    public void setUpSummaryFragment(String from, String to) {
        tripModel.setFrom(from);
        tripModel.setTo(to);
        UpdateFragmentUtil.loadFragment(new TripSummaryFragment(), getSupportFragmentManager(), R.id.trip_container);
    }


    @Override
    public void addTripObject(String summary, List<String> interestList) {
        tripModel.setSummary(summary);
        tripModel.setTripInterests(interestList);
        startWaiting("Please wait,while we Create your Trip.....", false);
        mAddTripPresenter.addTrip(tripModel, CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    private void setUpAddTripPresenterCallBack() {
        mAddTripPresenter.setUpAddTripPresenterCallBack(new IAddTripPresenterCallBack() {

            @Override
            public void onSuccessful() {
                finishWaiting();
                Intent intent = new Intent(getApplicationContext(), MatchesTripActivity.class);
                intent.putExtra(MatchesTripActivity.FROMTRIP, tripModel.getFrom());
                intent.putExtra(MatchesTripActivity.TOTRIP, tripModel.getTo());
                intent.putExtra(MatchesTripActivity.COUNTRY_TRIP, tripModel.getCountryName());
                startActivity(intent);
                finish();
                animateWithZoom();
            }

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
        });
    }

    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = AddTripActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }

    @Override
    public void onBackPressed() {
        if (getVisibleFragment() instanceof CountryListFragment) {
            finish();
            animateWithZoom();
        } else {
            super.onBackPressed();
        }
    }
}
