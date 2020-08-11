package com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.userModels.UserModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IChatMessagePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IChatMessagePresenterCallback;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.chatMessageInjectors.ChatMessageInjector;
import com.example.socialnetworkfortravellers.R;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.baseActivity.BaseActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Adapters.ChatAdapter;
import com.example.socialnetworkfortravellers.ViewLayer.Dialog.BaseProgressDialog;
import com.example.socialnetworkfortravellers.utilLayer.ConfigUtil;
import com.example.socialnetworkfortravellers.utilLayer.CurrentUserIDUtil;
import com.example.socialnetworkfortravellers.utilLayer.GlideUtil;
import com.example.socialnetworkfortravellers.utilLayer.StringEmptyUtil;
import com.google.firebase.database.ServerValue;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class ChatMessageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.profile_picture)
    ImageView mProfileImageView;
    @BindView(R.id.full_name)
    TextView mFullNameTextView;
    @BindView(R.id.add_text)
    EditText mAddTextEditText;

    @BindView(R.id.chat_messaging)
    RecyclerView mChatRecyclerView;

    @Inject
    IChatMessagePresenter mChatMessagePresenter;

    private String friendKey;
    private ChatAdapter chatAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        friendKey = getIntent().getStringExtra(ConfigUtil.FRIEND_KEY);
        setUpInject();
        initView();
        setUpMessageList();
        setUpView();
        setUpChatMessagePresenterCallback();
        getUserInfo();
    }

    private void setUpInject() {
        ChatMessageInjector.getSharedInjector().injectIn(this);
    }


    private void getUserInfo() {
        mChatMessagePresenter.getUserInfo(friendKey);
    }


    private void getMessageBranch() {
        mChatMessagePresenter.getMessageBranch(CurrentUserIDUtil.getInstance().getCurrentUserID(), friendKey);
    }


    private void setUpMessageList() {
        chatAdapter = new ChatAdapter(this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        mChatRecyclerView.setLayoutManager(mLayoutManager);
        mChatRecyclerView.setAdapter(chatAdapter);


    }


    private void setUpView() {

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

    }

    @OnClick(R.id.add_message)
    public void onSendMessageClick() {
        MessageModel messageModel = new MessageModel();
        messageModel.setUserKey(CurrentUserIDUtil.getInstance().getCurrentUserID());
        messageModel.setTimestamp(ServerValue.TIMESTAMP);
        messageModel.setMessageContent(mAddTextEditText.getText().toString());
        mChatMessagePresenter.addNewMessage(messageModel);
        mAddTextEditText.setText("");
    }


    private void setUpChatMessagePresenterCallback() {
        mChatMessagePresenter.setUpChatMessagePresenterCallback(new IChatMessagePresenterCallback() {
            @Override
            public void onMessagedAdded(@NonNull MessageModel messageModel) {
                chatAdapter.onNewMessageAdded(messageModel);
                mChatRecyclerView.smoothScrollToPosition(chatAdapter.getItemCount() - 1);
            }

            @Override
            public void userData(UserModel mUserModel) {
                getMessageBranch();
                if (!StringEmptyUtil.isEmptyString(mUserModel.getProfilePicture())) {
                    chatAdapter.setProfileImageUrl(mUserModel.getProfilePicture());
                    GlideUtil.loadImage(getApplicationContext(), mUserModel.getProfilePicture(), mProfileImageView);
                }
                mFullNameTextView.setText(mUserModel.getFullName());

            }

            @Override
            public void thisUserNotExists() {
                BaseProgressDialog.getInstance().showMessagesError("this is user Maybe banned or no longer exist", ChatMessageActivity.this);
            }

            @Override
            public void showMessageError(String message) {
                Toast.makeText(ChatMessageActivity.this, message, Toast.LENGTH_LONG).show();
            }

            @Override
            public void networkErrorMessage() {
                Toast.makeText(ChatMessageActivity.this, getString(R.string.no_internet), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        animateWithZoom();
    }
}