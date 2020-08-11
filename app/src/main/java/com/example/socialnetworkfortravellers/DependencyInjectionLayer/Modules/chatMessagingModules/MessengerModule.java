package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.chatMessagingModules;

import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.IMessengerPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Presenters.chatMessagePresenters.MessengerPresenter;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.BaseServices.getDataServices.IGetDataByUseSingleValueService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.UserManagementServices.getUserInfoServices.IGetUserInfoService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.GetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.chatMessagesServices.getAllMessageBranchService.IGetMessageBranchService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.GetListOfUserService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.getListOfUserServices.IGetListOfUserService;

import dagger.Module;
import dagger.Provides;

@Module
public class MessengerModule {

    @Provides
    IMessengerPresenter ProvidesMessengerPresenter(IGetMessageBranchService getMessageBranchService, IGetListOfUserService getListOfUserService) {
        return new MessengerPresenter(getMessageBranchService, getListOfUserService);
    }

    @Provides
    IGetMessageBranchService ProvidesGetMessageBranchService(IGetDataByUseSingleValueService getDataByUseSingleValueService) {
        return new GetMessageBranchService(getDataByUseSingleValueService);
    }


    @Provides
    IGetListOfUserService ProvidesGetListOfUserService(IGetUserInfoService getUserInfoService) {
        return new GetListOfUserService(getUserInfoService);
    }

}
