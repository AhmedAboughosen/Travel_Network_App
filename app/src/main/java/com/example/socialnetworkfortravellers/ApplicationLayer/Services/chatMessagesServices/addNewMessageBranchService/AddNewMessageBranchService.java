package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataServiceCallBack;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;

import java.util.HashMap;

import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.BRANCH_MESSAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.MESSAGE_KEY;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.MESSAGE_TEXT;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.TIMESTAMP;

public class AddNewMessageBranchService implements IAddNewMessageBranchService {


    private IAddNewMessageBranchServiceCallback mAddNewMessageBranchServiceCallback;
    private ISaveRawDataService mSaveInfoService;
    private MessageBranchModel mMessageModel;


    public AddNewMessageBranchService(ISaveRawDataService mSaveRawDataService) {
        this.mSaveInfoService = mSaveRawDataService;
    }


    /**
     * save message  data  in ChatMessage Branch user
     */
    @Override
    public void addNewMessageBranch(MessageBranchModel messageModel) {
        this.mMessageModel = messageModel;

        DatabaseReference messageBranchRef = FirebaseDatabase.getInstance().getReference().child(BRANCH_MESSAGE).child(mMessageModel.getUserKey()).child(mMessageModel.getFriendKey());

        mSaveInfoService.setDatabaseReference(messageBranchRef);

        HashMap<String, Object> chatMap = new HashMap<>();
        chatMap.put(MESSAGE_TEXT, mMessageModel.getLastMessage());
        chatMap.put(MESSAGE_KEY, mMessageModel.getMessageKey());
        chatMap.put(TIMESTAMP, ServerValue.TIMESTAMP);

        mSaveInfoService.setMapData(chatMap);
        mSaveInfoService.setUpSaveInfoServiceCallBack(new ISaveRawDataServiceCallBack() {
            @Override
            public void Successful() {
                mAddNewMessageBranchServiceCallback.saveMessageBranchSuccessful();
            }

            @Override
            public void showMessageError(String message) {
                mAddNewMessageBranchServiceCallback.showMessageError(message);
            }

            @Override
            public void noInternetFound() {
                mAddNewMessageBranchServiceCallback.noInternetFound();
            }
        });
        mSaveInfoService.updateData();

    }


    @Override
    public void setUpAddNewMessageBranchServiceCallback(IAddNewMessageBranchServiceCallback saveNewMessageServiceCallback) {
        this.mAddNewMessageBranchServiceCallback = saveNewMessageServiceCallback;
    }
}
