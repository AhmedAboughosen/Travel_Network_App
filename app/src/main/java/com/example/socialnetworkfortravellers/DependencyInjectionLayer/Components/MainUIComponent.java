package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.ActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.MainActivityModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.notificationModule.NotificationsModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.mainActivity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ContextModule.class, ActivityModule.class, MainActivityModule.class})
public interface MainUIComponent {

    void inject(MainActivity mainActivity);
}
