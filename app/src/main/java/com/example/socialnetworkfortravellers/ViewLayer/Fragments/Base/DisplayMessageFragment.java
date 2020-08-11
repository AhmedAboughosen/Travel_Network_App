package com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.IMessageFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class DisplayMessageFragment extends BaseFragment {


    @BindView(R.id.message)
    TextView mMesssageTextView;

    @BindView(R.id.btn_done)
    Button mButton;


    @BindView(R.id.lottieAnimationView)
    LottieAnimationView mLottieAnimationView;

    private String mMessage;
    private int mLottieAnimationID;
    private String mButtonTitle = "";
    private IMessageFragment mMessageFragment;
    private boolean isEnabled = true;

    public DisplayMessageFragment() {
        // Required empty public constructor
    }


    public void setMessage(String message) {
        mMessage = message;
    }

    public void setLottieAnimation(int LottieAnimationID) {
        mLottieAnimationID = LottieAnimationID;
    }


    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public void setButtonTitle(String buttonTitle) {
        mButtonTitle = buttonTitle;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_display_message, container, false);

        initView(view);
        mLottieAnimationView.setAnimation(mLottieAnimationID);
        mMesssageTextView.setText(mMessage);
        mButton.setVisibility(isEnabled ? View.VISIBLE : View.GONE);
        mButton.setText(mButtonTitle);
        mMessageFragment = (IMessageFragment) getActivity();
        return view;
    }

    @OnClick(R.id.btn_done)
    public void onButtonClick() {
        mMessageFragment.onButtonClick(mButtonTitle);
    }
}
