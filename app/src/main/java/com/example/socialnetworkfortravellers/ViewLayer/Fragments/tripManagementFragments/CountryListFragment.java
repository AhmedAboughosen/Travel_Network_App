package com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.CountryModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.ICountriesListPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.countriesListPresenters.ICountriesListPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors.CountryListInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.CountryListAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IAddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class CountryListFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;


    @Inject
    CountryListAdapter mCountryListAdapter;
    @Inject
    ICountriesListPresenter mCountriesListPresenter;
    private IAddTripActivity mAddTripActivity;
    private String mSelectedCountry;


    public CountryListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_country_list, container, false);

        try {
            setUpView();
            initView(view);
            setUpInjector();
            setUpRecyclerViewListOfCountries();
            setUpCountryListAdapterCallback();
            loadAllCountries();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return view;
    }

    private void setUpView() {
        mAddTripActivity = (IAddTripActivity) getActivity();
        mAddTripActivity.updateView("Destination", StateProgressBar.StateNumber.ONE);
    }

    private void setUpInjector() {
        CountryListInjector.getSharedInjector().injectIn(this);
    }


    /**
     * load All Countries
     */
    private void loadAllCountries() {
        mCountriesListPresenter.setUpCountriesListPresenterCallBack(new ICountriesListPresenterCallBack() {
            @Override
            public void getAllCountry(List<CountryModel> list) {
                mProgressBar.setVisibility(View.GONE);
                mCountryListAdapter.updateCountries(list);
                runLayoutAnimation(mRecyclerView);
            }

            @Override
            public void noCountry(String str) {
                mProgressBar.setVisibility(View.GONE);
                showMessagesError(str);
            }
        });

        mCountriesListPresenter.getALLCountries();
    }

    /**
     * setUpRecyclerViewListOfCountries
     */
    private void setUpRecyclerViewListOfCountries() {
        InitializeRecyclerViewUtil.initVerticalRecyclerViewWithLine(mRecyclerView, getActivity(), mCountryListAdapter);

    }

    protected void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }


    /**
     * setUpCountryListAdapterCallback
     */
    private void setUpCountryListAdapterCallback() {
        mCountryListAdapter.setUpCountryListAdapterCallback(country_Name -> {
            mSelectedCountry = country_Name;
            OkCancelDialog okCancelDialog = new OkCancelDialog(getActivity());
            okCancelDialog.show("Are you sure you want to choose this country?", "", new IOkCancelDialogCallBack() {
                @Override
                public void onCancelClick() {
                    okCancelDialog.dismiss();
                }

                @Override
                public void onOkClick() {
                    okCancelDialog.dismiss();
                    mAddTripActivity.setUpDateFragment(mSelectedCountry);
                }
            });
        });
    }

}
