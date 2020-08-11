package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.chatMessageInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.ChatMessageComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.chatMessageComponents.DaggerChatMessageComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.chatMessageActivities.ChatMessageActivity;

public class ChatMessageInjector {


    private static ChatMessageInjector mInjector;

    private ChatMessageInjector() {
    }

    public static ChatMessageInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new ChatMessageInjector();
        }
        return mInjector;
    }


    public void injectIn(ChatMessageActivity chatMessageActivity) {
        ChatMessageComponent mChatMessageComponent = DaggerChatMessageComponent.builder()
                .contextModule(new ContextModule(chatMessageActivity))
                .build();

        mChatMessageComponent.setUpInject(chatMessageActivity);

    }
}
