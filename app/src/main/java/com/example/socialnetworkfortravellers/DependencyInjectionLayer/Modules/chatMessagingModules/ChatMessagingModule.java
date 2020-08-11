package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.chatMessagingModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.ChatMessagePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IChatMessagePresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.GetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.getDataByUseChildEventListenerServices.IGetDataByUseChildEventListenerService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.saveRawDataServices.ISaveRawDataService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.GetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService.AddNewMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.addNewMessageBranchService.IAddNewMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.GetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService.GetChatMessageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getChatMessageService.IGetChatMessageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService.ISaveNewMessageService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.saveNewMessageService.SaveNewMessageService;

import dagger.Module;
import dagger.Provides;

@Module
public class ChatMessagingModule {

    @Provides
    IChatMessagePresenter ProvidesChatMessagePresenter(IGetMessageBranchService getMessageBranchService, IAddNewMessageBranchService addNewMessageBranchService, IGetChatMessageService getChatMessageService
            , ISaveNewMessageService saveNewMessageService, IGetUserInfoService getUserInfoService) {
        return new ChatMessagePresenter(getMessageBranchService, addNewMessageBranchService, getChatMessageService, saveNewMessageService, getUserInfoService);
    }


    @Provides
    IGetMessageBranchService ProvidesGetMessageBranchService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetMessageBranchService(getDataByUseSingleValueService);
    }

    @Provides
    IAddNewMessageBranchService ProvidesAddNewMessageBranchService(ISaveRawDataService mSaveRawDataService) {
        return new AddNewMessageBranchService(mSaveRawDataService);
    }


    @Provides
    IGetChatMessageService ProvidesGetChatMessageService(IGetDataByUseChildEventListenerService getDataByUseChildEventListenerService) {
        return new GetChatMessageService(getDataByUseChildEventListenerService);
    }


    @Provides
    IGetDataByUseChildEventListenerService ProvidesGetDataByUseChildEventListenerService(Context context) {
        return new GetDataByUseChildEventListenerService(context);
    }

    @Provides
    ISaveNewMessageService ProvidesSaveNewMessageService(ISaveRawDataService saveRawDataService) {
        return new SaveNewMessageService(saveRawDataService);
    }


}
