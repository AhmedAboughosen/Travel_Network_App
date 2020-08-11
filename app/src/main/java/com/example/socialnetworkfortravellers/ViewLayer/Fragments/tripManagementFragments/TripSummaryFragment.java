package com.example.socialnetworkfortravellers.ViewLayer.Fragments.tripManagementFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.InterestTripAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IAddTripActivity;
import com.example.socialnetworkfortravellers.utilLayer.InitializeRecyclerViewUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class TripSummaryFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.details)
    TextInputEditText mDetailsTextInputEditText;

    @BindView(R.id.userIDTextInputLayout)
    TextInputLayout mDetailsTextInputLayout;


    private IAddTripActivity mAddTripActivity;
    private InterestTripAdapter mInterestTripAdapter;

    public TripSummaryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trip_summary, container, false);

        initView(view);
        setUpView();
        setUpRecyclerView();
        return view;
    }


    private void setUpView() {
        mAddTripActivity = (IAddTripActivity) getActivity();
        mAddTripActivity.updateView("Additional Info", StateProgressBar.StateNumber.THREE);
    }

    private void setUpRecyclerView() {
        mInterestTripAdapter = new InterestTripAdapter(getActivity());
        InitializeRecyclerViewUtil.initVerticalGridLayoutRecyclerView(recyclerView, getActivity(), mInterestTripAdapter, 4);
    }


    @OnClick(R.id.confirm)
    public void onConfirmButtonClick() {
        if (mDetailsTextInputEditText.getText().toString().replace(" ", "").isEmpty() || mDetailsTextInputEditText.getText().toString().replace(" ", "").length() <= 10) {
            mDetailsTextInputLayout.setError("please tell us more  about your trip.");
            mDetailsTextInputLayout.requestFocus();
        } else {
            List<String> interestList = new ArrayList<>();
            for (int i = 0; i < mInterestTripAdapter.getInterests().size(); i++) {
                if (mInterestTripAdapter.getInterests().get(i).isMarked()) {
                    interestList.add(mInterestTripAdapter.getInterests().get(i).getInterestName());
                }
            }
            mAddTripActivity.addTripObject(mDetailsTextInputEditText.getText().toString(), interestList);
        }
    }
}
