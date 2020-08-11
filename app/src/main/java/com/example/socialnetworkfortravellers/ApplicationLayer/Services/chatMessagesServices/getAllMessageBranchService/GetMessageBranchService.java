package com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService;

import androidx.annotation.NonNull;

import com.example.socialnetworkfortravellers.ApplicationLayer.DomainModels.MessageBranchModel;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueServiceCallBack;
import com.example.socialnetworkfortravellers.utilLayer.ConvertTimeUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.BRANCH_MESSAGE;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.MESSAGE_KEY;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.MESSAGE_TEXT;
import static com.example.socialnetworkfortravellers.nodesLayer.ChatMessageNode.TIMESTAMP;


public class GetMessageBranchService implements IGetMessageBranchService {


    private IGetMessageBranchServiceCallback mGetMessageBranchServiceCallback;
    private IGetDataByUseSingleValueService mGetDataByUseSingleValueService;


    public GetMessageBranchService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        this.mGetDataByUseSingleValueService = getDataByUseSingleValueService;
    }


    /**
     * @param userKey
     */
    @Override
    public void getAllMessageBranch(String userKey) {

        DatabaseReference chatBranchesRef = FirebaseDatabase.getInstance().getReference().child(BRANCH_MESSAGE).child(userKey);

        mGetDataByUseSingleValueService.setUpDatabaseReference(chatBranchesRef);

        List<MessageBranchModel> messageBranchModels = new ArrayList<>();
        List<String> friendsList = new ArrayList<>();

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        for (DataSnapshot friendKey : dataSnapshot.getChildren()) {
                            String key = friendKey.getKey();
                            DataSnapshot friendKeySnapshot = dataSnapshot.child(key);
                            MessageBranchModel messageBranchModel = new MessageBranchModel();
                            messageBranchModel.setFriendKey(key);
                            friendsList.add(key);
                            if (friendKeySnapshot.hasChild(MESSAGE_TEXT)) {
                                messageBranchModel.setLastMessage(Objects.requireNonNull(friendKeySnapshot.child(MESSAGE_TEXT).getValue()).toString());
                            }

                            if (friendKeySnapshot.hasChild(MESSAGE_KEY)) {
                                messageBranchModel.setMessageKey(Objects.requireNonNull(friendKeySnapshot.child(MESSAGE_KEY).getValue()).toString());
                            }

                            if (friendKeySnapshot.hasChild(TIMESTAMP)) {
                                messageBranchModel.setOrderMessage(ConvertTimeUtil.toTimeStampToSeconds(Objects.requireNonNull(friendKeySnapshot.child(TIMESTAMP).getValue()).toString()));
                                messageBranchModel.setTimestamp(Objects.requireNonNull(friendKeySnapshot.child(TIMESTAMP).getValue()));
                            }

                            messageBranchModels.add(messageBranchModel);
                        }
                        mGetMessageBranchServiceCallback.listOfMessageBranch(messageBranchModels, friendsList);
                    } else {
                        mGetMessageBranchServiceCallback.currentUserDoesNotHaveFriends();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetMessageBranchServiceCallback.showMessageError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetMessageBranchServiceCallback.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetMessageBranchServiceCallback.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }


    /**
     * @param userKey
     */
    @Override
    public void getMessageBranch(String userKey, String friendKey) {

        DatabaseReference chatBranchesRef = FirebaseDatabase.getInstance().getReference().child(BRANCH_MESSAGE).child(userKey).child(friendKey);

        mGetDataByUseSingleValueService.setUpDatabaseReference(chatBranchesRef);

        mGetDataByUseSingleValueService.setUpGetDataServiceCallBack(new IGetDataByUseSingleValueServiceCallBack() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.exists()) {

                        MessageBranchModel messageBranchModel = new MessageBranchModel();
                        messageBranchModel.setFriendKey(friendKey);
                        messageBranchModel.setUserKey(userKey);

                        if (dataSnapshot.hasChild(MESSAGE_TEXT)) {
                            messageBranchModel.setLastMessage(Objects.requireNonNull(dataSnapshot.child(MESSAGE_TEXT).getValue()).toString());
                        }

                        if (dataSnapshot.hasChild(MESSAGE_KEY)) {
                            messageBranchModel.setMessageKey(Objects.requireNonNull(dataSnapshot.child(MESSAGE_KEY).getValue()).toString());
                        }

                        if (dataSnapshot.hasChild(TIMESTAMP)) {
                            messageBranchModel.setTimestamp(Objects.requireNonNull(dataSnapshot.child(TIMESTAMP).getValue()));
                        }
                        mGetMessageBranchServiceCallback.getMessageBranch(messageBranchModel);
                    } else {
                        mGetMessageBranchServiceCallback.thisFriendDoesNotExists();
                    }

                } catch (Exception ex) {
                    ex.printStackTrace();
                    mGetMessageBranchServiceCallback.showMessageError(ex.getMessage());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mGetMessageBranchServiceCallback.showMessageError(databaseError.getMessage());
            }

            @Override
            public void noInternetFound() {
                mGetMessageBranchServiceCallback.noInternetFound();
            }
        });

        mGetDataByUseSingleValueService.getData();
    }


    @Override
    public void setUpGetMessageBranchServiceCallback(IGetMessageBranchServiceCallback getMessageBranchServiceCallback) {
        this.mGetMessageBranchServiceCallback = getMessageBranchServiceCallback;
    }


}
