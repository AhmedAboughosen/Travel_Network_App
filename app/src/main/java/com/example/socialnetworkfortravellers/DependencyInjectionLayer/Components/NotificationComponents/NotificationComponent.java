package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.NotificationComponents;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.notificationModule.NotificationsModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.notificationFragments.NotificationFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, NotificationsModule.class})
public interface NotificationComponent {

    void inject(NotificationFragment notificationFragment);
}