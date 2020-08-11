package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode;
import com.example.socialnetworkfortravellers.utilLayer.StringConfigUtil;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SaveNewMessageService implements ISaveNewMessageService {

    private ISaveNewMessageServiceCallback mSaveNewMessageServiceCallback;
    private ISaveRawDataService mSaveInfoService;
    private MessageModel mMessageModel;


    public SaveNewMessageService(ISaveRawDataService mSaveRawDataService) {
        this.mSaveInfoService = mSaveRawDataService;
    }


    /**
     * save message  data  in ChatMessage Branch user
     */
    @Override
    public void addNewMessage(MessageModel messageModel, String messageKey) {
        this.mMessageModel = messageModel;


        DatabaseReference messageRef = FirebaseDatabase.getInstance().getReference().child(ChatMessageNode.CHAT_MESSAGE).child(messageKey);

        String messageNewKey = messageRef.push().getKey();

        if (messageNewKey != null) {
            messageRef = messageRef.child(messageNewKey);
            mSaveInfoService.setDatabaseReference(messageRef);
            HashMap<String, Object> chatMap = new HashMap<>();
            chatMap.put(ChatMessageNode.USER_KEY, mMessageModel.getUserKey());
            chatMap.put(ChatMessageNode.MESSAGE_TEXT, mMessageModel.getMessageContent());
            chatMap.put(ChatMessageNode.TIMESTAMP, mMessageModel.getTimestamp());

            mSaveInfoService.setMapData(chatMap);
            mSaveInfoService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
                @Override
                public void Successful() {
                    mSaveNewMessageServiceCallback.saveMessageSuccessful();
                }

                @Override
                public void showMessageError(String message) {
                    mSaveNewMessageServiceCallback.showMessageError(message);
                }

                @Override
                public void noInternetFound() {
                    mSaveNewMessageServiceCallback.noInternetFound();
                }
            });
            mSaveInfoService.updateData();

        } else {
            mSaveNewMessageServiceCallback.showMessageError(StringConfigUtil.MESSAGE_PROBLEM);
        }


    }


    @Override
    public void setUpSaveNewMessageServiceCallback(ISaveNewMessageServiceCallback saveNewMessageServiceCallback) {
        this.mSaveNewMessageServiceCallback = saveNewMessageServiceCallback;
    }
}
