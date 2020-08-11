package com.example.socialnetworkfortravellers.ViewLayer.Activties.InterestManagementAcitivites;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters.IAddInterestPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.InterestManagementPresenters.addInterestPresenters.IAddInterestPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.InterestManagementInjectors.AddInterestInjector;
import com.example.socialnetworkfortravellers.InfrastructureLayer.sharedPreferencesManager.ConfigurationSharedPref;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.suggestionActivity.FriendSuggestionActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.InterestAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ItemOffsetDecorationUtil;
import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class AddInterestActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView mListOfInterestRecyclerView;

    @Inject
    IAddInterestPresenter mAddInterestPresenter;


    @Inject
    InterestAdapter mInterestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_interest);
        ConfigurationSharedPref.getInstance().setUpStartUpActivity(getApplicationContext(), ConfigurationSharedPref.ADD_INTEREST);
        setupInject();
        setUpAddInterestPresenterCallBack();
        initView();
        setUpRecyclerView();
    }

    private void setupInject() {
        AddInterestInjector.getSharedInjector().injectIn(this);
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


    @OnClick(R.id.next)
    public void onNextButtonClick() {
        startWaiting("Please wait, while we updating your Interests...", false);
        mAddInterestPresenter.addInterest(mInterestAdapter.getInterests());
    }


    /**
     * setUpAddInterestPresenterCallBack
     */
    private void setUpAddInterestPresenterCallBack() {
        mAddInterestPresenter.setUpAddInterestPresenterCallBack(new IAddInterestPresenterCallBack() {
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


            @Override
            public void addInterestSuccessful() {
                finishWaiting();
                SendToActivityUtil.getInstance().SendUserToOtherActivityWithTransitionLeftin_out(AddInterestActivity.this, FriendSuggestionActivity.class);
                finish();
                finishAffinity();
            }


        });
    }

    @Override
    public void onBackPressed() {
        OkCancelDialog okCancelDialog = new OkCancelDialog(this);
        okCancelDialog.show("Are you sure you want to leave this step?", "if yes, you can set your own interest later.", new IOkCancelDialogCallBack() {
            @Override
            public void onCancelClick() {
                okCancelDialog.dismiss();
            }

            @Override
            public void onOkClick() {
                okCancelDialog.dismiss();
                finish();
                finishAffinity();
            }
        });
    }
}
