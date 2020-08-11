package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.chatMessageInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.DaggerMessengerComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.MessengerComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.MessengerActivity;

public class MessengerInjector {


    private static MessengerInjector mInjector;

    private MessengerInjector() {
    }

    public static MessengerInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new MessengerInjector();
        }
        return mInjector;
    }


    public void injectIn(MessengerActivity messengerActivity) {
        MessengerComponent mMessengerComponent = DaggerMessengerComponent.builder()
                .contextModule(new ContextModule(messengerActivity))
                .build();

        mMessengerComponent.setUpInject(messengerActivity);

    }
}
