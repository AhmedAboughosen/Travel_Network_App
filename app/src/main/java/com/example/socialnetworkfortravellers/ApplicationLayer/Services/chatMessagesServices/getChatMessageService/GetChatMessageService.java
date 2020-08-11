package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerServiceCallBack;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.CHAT_MESSAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.MESSAGE_TEXT;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.TIMESTAMP;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.USER_KEY;

public class GetChatMessageService implements IGetChatMessageService {


    private IGetDataByUseChildEventListenerService mGetDataByUseChildEventListenerService;
    private IGetChatMessageServiceCallback mGetChatMessageServiceCallback;

    public GetChatMessageService(IGetDataByUseChildEventListenerService getDataByUseChildEventListenerService) {
        this.mGetDataByUseChildEventListenerService = getDataByUseChildEventListenerService;

    }


    @Override
    public void setUpGetChatMessageServiceCallback(IGetChatMessageServiceCallback getChatMessageServiceCallback) {
        this.mGetChatMessageServiceCallback = getChatMessageServiceCallback;
    }


    @Override
    public void listenForMessages(String messageKey) {


        DatabaseReference chatRef = FirebaseDatabase.getInstance().getReference().child(CHAT_MESSAGE).child(messageKey);

        mGetDataByUseChildEventListenerService.setUpDatabaseReference(chatRef);
        mGetDataByUseChildEventListenerService.setUpGetDataByUseChildEventListenerServiceCallBack(new IGetDataByUseChildEventListenerServiceCallBack() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                mGetChatMessageServiceCallback.onMessagedAdded(GetMessage(dataSnapshot));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetChatMessageServiceCallback.showMessageError(databaseError.getMessage());
            }

            @Override
            public void showMessageError(String message) {
                mGetChatMessageServiceCallback.showMessageError(message);

            }

            @Override
            public void noInternetFound() {
                mGetChatMessageServiceCallback.noInternetFound();
            }
        });

        mGetDataByUseChildEventListenerService.getData();
    }

    /**
     * pass dataSnapshot get User Object
     *
     * @param dataSnapshot
     */
    private MessageModel GetMessage(DataSnapshot dataSnapshot) {

        MessageModel messageModel = new MessageModel();

        if (dataSnapshot.exists()) {

            messageModel.setMessageKey(dataSnapshot.getKey());

            if (dataSnapshot.hasChild(MESSAGE_TEXT)) {
                messageModel.setMessageContent(Objects.requireNonNull(dataSnapshot.child(MESSAGE_TEXT).getValue()).toString());
            }


            if (dataSnapshot.hasChild(TIMESTAMP)) {
                messageModel.setTimestamp(Objects.requireNonNull(dataSnapshot.child(TIMESTAMP).getValue()).toString());
            }


            if (dataSnapshot.hasChild(USER_KEY)) {
                messageModel.setUserKey(Objects.requireNonNull(dataSnapshot.child(USER_KEY).getValue()).toString());
            }

        }

        return messageModel;

    }
}
