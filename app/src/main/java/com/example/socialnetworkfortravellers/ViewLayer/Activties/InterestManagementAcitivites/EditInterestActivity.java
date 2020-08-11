package com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.InterestModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters.IEditInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.editInterestPresenters.IEditInterestPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.InterestManagementInjectors.EditInterestInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.InterestAdapter;
import com.example.socialnetworkfortravellers.utilLayer.ItemOffsetDecorationUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class EditInterestActivity extends BaseActivity {


    @BindView(R.id.list)
    RecyclerView mListOfInterestRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Inject
    InterestAdapter mInterestAdapter;
    @Inject
    IEditInterestPresenter mEditInterestPresenter;

    private boolean isState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_interest);

        try {
            setUpInject();
            initView();

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            Drawable navIcon = mToolbar.getNavigationIcon();
            navIcon.setColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN);

            setUpRecyclerView();
            setEditInterestPresenterCallBack();
            getInterestOfUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setUpInject() {
        EditInterestInjector.getSharedInjector().injectIn(this);
    }

    /**
     * setUpListOfImageRecyclerView
     */
    private void setUpRecyclerView() {
        // set a GridLayoutManager with default vertical orientation and 2 number of columns
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        // set main_logo LinearLayoutManager with default vertical orientation
        mListOfInterestRecyclerView.setLayoutManager(gridLayoutManager);


        ItemOffsetDecorationUtil itemDecoration = new ItemOffsetDecorationUtil(getApplicationContext(), R.dimen.item_offset);
        mListOfInterestRecyclerView.addItemDecoration(itemDecoration);


        // call the constructor of CustomAdapter to send the reference and data to Adapter
        mListOfInterestRecyclerView.setAdapter(mInterestAdapter); // set the Adapter to RecyclerView
    }


    public void getInterestOfUser() {
        mEditInterestPresenter.getInterestOfUser();
    }


    public void setEditInterestPresenterCallBack() {
        mEditInterestPresenter.setEditInterestPresenterCallBack(new IEditInterestPresenterCallBack() {
            @Override
            public void showMessageError(String message) {
                mProgressBar.setVisibility(View.GONE);
                finishWaiting();
                showMessagesError(message);
            }

            @Override
            public void noInternetFound() {
                mProgressBar.setVisibility(View.GONE);
                finishWaiting();
                showMessagesError("No Internet Found.");
            }

            @Override
            public void updateMarkedInterest(List<InterestModel> list) {
                mInterestAdapter.updateInterest(list);
                isState = true;
            }

            @Override
            public void getDataSuccessfully() {
                mProgressBar.setVisibility(View.GONE);
                mEditInterestPresenter.updateMarkedInterest(mInterestAdapter.getInterests());
            }

            @Override
            public void noItemSelected() {
                finishWaiting();
                Toast.makeText(EditInterestActivity.this, "Please Select Some of Interest!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void updateDataSuccessful() {
                finishWaiting();
                Toast.makeText(EditInterestActivity.this, "Interest Updating successfully", Toast.LENGTH_LONG).show();
                finish();
                animateWithZoom();
            }
        });
    }


    @OnClick(R.id.update_data)
    public void UpdateClickButton() {
        if (isState) {
            startWaiting("Please wait, while we updating your Interests...", false);
            mEditInterestPresenter.updateInterest(mInterestAdapter.getInterests());
        } else {
            Toast.makeText(EditInterestActivity.this, "Please wait while we download your Interests!", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}
