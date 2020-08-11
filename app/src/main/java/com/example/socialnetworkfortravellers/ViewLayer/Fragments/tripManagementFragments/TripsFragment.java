package com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.TripModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters.IGetUserTripsPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.TripManagementPresenters.getUserTripsPresenters.IGetUserTripsPresenterCallBack;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.tripManagementInjectors.GetTripsInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.tripManagementActivities.TripInformationActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.DisplayTripAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOnTripItem;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.FRIEND_KEY;
import static com.example.socialnetworkfortravellers.utilLayer.ConfigUtil.USER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripsFragment extends BaseFragment implements IOnTripItem {


    @BindView(R.id.progress_bar)
    ProgressBar mProgressBar;

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @Inject
    DisplayTripAdapter mDisplayTripAdapter;
    @Inject
    IGetUserTripsPresenter mGetUserTripsPresenter;

    private String userKey;
    private int removeAbleIndex = 0;

    public TripsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip, container, false);

        setUpInject();
        initView(view);
        getBundleData();
        setUpRecyclerView();
        setUpGetUserTripsPresenterCallBack();
        geTrips();
        return view;
    }


    /**
     * setUpInject
     */
    private void setUpInject() {
        GetTripsInjector.getSharedInjector().injectIn(this);
    }

    /**
     * get key of current user.
     */
    private void getBundleData() {
        if (getArguments() != null) {
            userKey = getArguments().getString(USER_KEY);
        }
    }

    /**
     * geTrips
     */
    private void geTrips() {
        mGetUserTripsPresenter.geTrips(userKey);
    }


    private void setUpGetUserTripsPresenterCallBack() {
        mGetUserTripsPresenter.setUpGetUserTripsPresenterCallBack(new IGetUserTripsPresenterCallBack() {
            @Override
            public void noRelatedTrips() {
                mProgressBar.setVisibility(View.GONE);
                ConfigUtil.activeFragment = "trip";
                DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
                displayMessageFragment.setLottieAnimation(R.raw.sad_search);
                displayMessageFragment.setMessage("No Trips yet.");
                displayMessageFragment.setButtonTitle("add new trip");
                if (!FRIEND_KEY.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())){
                    displayMessageFragment.setEnabled(false);
                }

                UpdateFragmentUtil.loadFragment(displayMessageFragment, getFragmentManager(), R.id.load_fragment_trip);
            }

            @Override
            public void deleteTripSuccessful() {
                finishWaiting();
                mDisplayTripAdapter.removeAt(removeAbleIndex);
                showMessagesSuccess("Trip has been successfully deleted");
            }

            @Override
            public void getAllUserTrips(List<TripModel> tripModelList) {
                mProgressBar.setVisibility(View.GONE);
                mDisplayTripAdapter.updateDate(tripModelList);
                runLayoutAnimation(mRecyclerView);
            }

            @Override
            public void showMessageError(String message) {
                finishWaiting();
                mProgressBar.setVisibility(View.GONE);
                showMessagesError(message);
            }

            @Override
            public void networkErrorMessage() {
                finishWaiting();
                mProgressBar.setVisibility(View.GONE);
                showMessagesError(getString(R.string.no_internet));
            }
        });
    }

    private void setUpRecyclerView() {
        mDisplayTripAdapter.setIOnTripItem(this);
        InitializeRecyclerViewUtil.initVerticalRecyclerView(mRecyclerView, getContext(), mDisplayTripAdapter);
    }


    @Override
    public void onTripClick(TripModel tripModel, int selectImage) {

        Intent intent = new Intent(getActivity(), TripInformationActivity.class);

        intent.putExtra(TripInformationActivity.SELECTED_IMAGE, selectImage);
        intent.putExtra(TripInformationActivity.TRIP_OBJECT, tripModel);

        startActivity(intent);
        animateWithZoom();
    }

    @Override
    public void onDeleteTrip(String tripKey, int index) {
        if (userKey.equals(CurrentUserIDUtil.getInstance().getCurrentUserID())) {
            OkCancelDialog okCancelDialog = new OkCancelDialog(getActivity());
            okCancelDialog.show("are you sure you want to delete this trip?", "", new IOkCancelDialogCallBack() {
                @Override
                public void onCancelClick() {
                    okCancelDialog.dismiss();
                }

                @Override
                public void onOkClick() {
                    okCancelDialog.dismiss();
                    removeAbleIndex = index;
                    startWaiting("Please wait, while we are deleting your Trip...", false);
                    mGetUserTripsPresenter.deleteTrip(tripKey, userKey);
                }
            });
        }
    }

    /**
     * runLayoutAnimation
     *
     * @param recyclerView
     */
    protected void runLayoutAnimation(final RecyclerView recyclerView) {
        final Context context = recyclerView.getContext();
        final LayoutAnimationController controller =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_from_bottom);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.scheduleLayoutAnimation();
    }


}
