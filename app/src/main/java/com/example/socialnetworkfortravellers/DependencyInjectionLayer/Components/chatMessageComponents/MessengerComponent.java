package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetDataByUseSingleValueModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.GetUserInfoModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.chatMessagingModules.MessengerModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.MessengerActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {ContextModule.class, MessengerModule.class, GetUserInfoModule.class, GetDataByUseSingleValueModule.class})
public interface MessengerComponent {
    void setUpInject(MessengerActivity messengerActivity);
}
