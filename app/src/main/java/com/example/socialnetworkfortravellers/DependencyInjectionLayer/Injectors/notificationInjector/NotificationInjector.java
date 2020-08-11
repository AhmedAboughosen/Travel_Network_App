package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.notificationInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.NotificationComponents.DaggerNotificationComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.NotificationComponents.NotificationComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.ChatMessageComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.DaggerChatMessageComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.ChatMessageActivity;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.notificationFragments.NotificationFragment;

public class NotificationInjector {


    private static NotificationInjector mInjector;

    private NotificationInjector() {
    }

    public static NotificationInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new NotificationInjector();
        }
        return mInjector;
    }


    public void injectIn(NotificationFragment notificationFragment) {
        NotificationComponent mNotificationComponent = DaggerNotificationComponent.builder()
                .contextModule(new ContextModule(notificationFragment.getActivity()))
                .build();

        mNotificationComponent.inject(notificationFragment);

    }
}
