package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.SaveRawDataModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.chatMessagingModules.ChatMessagingModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.ChatMessageActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ContextModule.class, ChatMessagingModule.class, GetUserInfoModule.class, SaveRawDataModule.class, GetDataByUseSingleValueModule.class})
public interface ChatMessageComponent {
    void setUpInject(ChatMessageActivity chatMessageActivity);
}
