package com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.SelectedInterestTripAdapter;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class TripInformationActivity extends BaseActivity {

    public static final String SELECTED_IMAGE = "SelectedImage";
    public static final String TRIP_OBJECT = "TripObject";
    @BindView(R.id.imageID)
    ImageView mCountryPhotoImageView;
    @BindView(R.id.country_name)
    TextView mCountryNameTextView;
    @BindView(R.id.date_time)
    TextView mDateTimeTextView;
    @BindView(R.id.summary)
    TextView mSummaryTextView;
    @BindView(R.id.list)
    RecyclerView mRecyclerView;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    private TripModel tripModel;
    private int imageID;
    private SelectedInterestTripAdapter mSelectedInterestTripAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_information);

        initView();
        setUpObject();
        dataBinding();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Drawable navIcon = mToolbar.getNavigationIcon();
        navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

    }


    private void setUpObject() {
        tripModel = (TripModel) getIntent().getSerializableExtra(TRIP_OBJECT);
        imageID = getIntent().getIntExtra(TripInformationActivity.SELECTED_IMAGE, 0);
        mSelectedInterestTripAdapter = new SelectedInterestTripAdapter(tripModel.getTripInterests());
    }


    /**
     * data Binding
     */
    private void dataBinding() {
        mCountryPhotoImageView.setImageResource(imageID);
        mCountryNameTextView.setText(tripModel.getCountryName());
        mDateTimeTextView.setText("from " + tripModel.getFrom() + " to " + tripModel.getTo());
        mSummaryTextView.setText(tripModel.getSummary());
        InitializeRecyclerViewUtil.initVerticalGridLayoutRecyclerView(mRecyclerView, getApplicationContext(), mSelectedInterestTripAdapter, 4);
    }


    @OnClick(R.id.trip_matches)
    public void onTripMatches() {

        Intent intent = new Intent(getApplicationContext(), MatchesTripActivity.class);
        intent.putExtra(MatchesTripActivity.FROMTRIP, tripModel.getFrom());
        intent.putExtra(MatchesTripActivity.TOTRIP, tripModel.getTo());
        intent.putExtra(MatchesTripActivity.COUNTRY_TRIP, tripModel.getCountryName());

        startActivity(intent);
        animateWithZoom();
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
