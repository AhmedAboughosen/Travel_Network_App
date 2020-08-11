package com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.fragment.app.Fragment;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.OkCancelDialog;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IAddTripActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IOkCancelDialogCallBack;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripDateFragment extends BaseFragment {

    @BindView(R.id.startDateID)
    DatePicker mStartDatePicker;
    @BindView(R.id.endDateID)
    DatePicker mEndDatePicker;


    @BindView(R.id.select_date)
    Button mSelectDate;

    private IAddTripActivity mAddTripActivity;
    private String from, to;

    public TripDateFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_date, container, false);
        initView(view);
        setUpView();
        return view;
    }

    private void setUpView() {
        mAddTripActivity = (IAddTripActivity) getActivity();
        mAddTripActivity.updateView("Trip Date", StateProgressBar.StateNumber.TWO);
    }

    @OnClick(R.id.select_date)
    public void onSelectDateButtonClick() {

        if (checkIfDateCorrect()) {
            OkCancelDialog okCancelDialog = new OkCancelDialog(getActivity());
            okCancelDialog.show("Are you sure you want to choose these dates? ", " ", new IOkCancelDialogCallBack() {
                @Override
                public void onCancelClick() {
                    okCancelDialog.dismiss();
                }

                @Override
                public void onOkClick() {
                    okCancelDialog.dismiss();
                    mAddTripActivity.setUpSummaryFragment(from, to);
                }
            });
        } else {
           BaseProgressDialog.getInstance().showMessagesError("please select correct date." , getActivity());
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
}
