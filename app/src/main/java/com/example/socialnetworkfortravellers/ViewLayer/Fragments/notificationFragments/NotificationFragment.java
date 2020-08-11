package com.example.socialnetworkfortravellers.ViewLayer.Fragments.notificationFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.NotificationsModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters.IGetNotificationsPresenterCallback;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.notificationPresenters.INotificationPresenter;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.notificationInjector.NotificationInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.profileActivity.FriendProfileActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.NotificationAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.BaseFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.Base.DisplayMessageFragment;
import com.example.socialnetworkfortravellers.ViewLayer.Interfaces.INotificationAdapterCallback;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.UpdateFragmentUtil;

import javax.inject.Inject;

import butterknife.BindView;


public class NotificationFragment extends BaseFragment {

    @BindView(R.id.list)
    RecyclerView mRecyclerView;

    @BindView(R.id.load_users)
    FrameLayout mFrameLayout;


    private NotificationAdapter notificationAdapter;

    @Inject
    INotificationPresenter mNotificationPresenter;


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);

        setUpInject();
        initView(view);
        setUpRecyclerView();
        getNotifications();

        return view;
    }

    private void setUpInject() {
        NotificationInjector.getSharedInjector().injectIn(this);
    }

    private void setUpRecyclerView() {
        notificationAdapter = new NotificationAdapter(getActivity());
        notificationAdapter.updateNotifications(null);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);

       // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), linearLayoutManager.getOrientation());


        notificationAdapter.setUpNotificationAdapterCallback(key -> {
            Intent intent = new Intent(getActivity(), FriendProfileActivity.class);
            intent.putExtra("FriendKey", key);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
            animateWithZoom();
        });
       // mRecyclerView.addItemDecoration(dividerItemDecoration);
        mRecyclerView.setAdapter(notificationAdapter);

    }


    private void getNotifications() {
        mNotificationPresenter.setUpGetNotificationsPresenterCallback(new IGetNotificationsPresenterCallback() {
            @Override
            public void onGetNotifications(NotificationsModel notificationsModel) {
                notificationAdapter.removeNullItem();
                notificationAdapter.updateNotifications(notificationsModel);
            }

            @Override
            public void noNotifications() {
                mFrameLayout.removeAllViews();
                DisplayMessageFragment displayMessageFragment = new DisplayMessageFragment();
                displayMessageFragment.setLottieAnimation(R.raw.notification);
                displayMessageFragment.setMessage("No Notifications Found.");
                displayMessageFragment.setEnabled(false);
                UpdateFragmentUtil.loadFragment(displayMessageFragment, getFragmentManager(), R.id.load_users);
            }

            @Override
            public void showMessageError(String message) {
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void networkErrorMessage() {
                Toast.makeText(getActivity(), getResources().getString(R.string.no_internet), Toast.LENGTH_SHORT).show();
            }
        });

        mNotificationPresenter.getNotifications(CurrentUserIDUtil.getInstance().getCurrentUserID());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mNotificationPresenter.removeEventListener();
    }
}