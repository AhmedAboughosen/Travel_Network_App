package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerFriendSuggestionComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.FriendSuggestionComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.suggestionFragments.FriendSuggestionFragment;

public class FriendSuggestionInjector {

    private static FriendSuggestionInjector mInjector;

    private FriendSuggestionInjector() {
    }

    public static FriendSuggestionInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new FriendSuggestionInjector();
        }
        return mInjector;
    }


    public void injectIn(FriendSuggestionFragment friendSuggestionFragment) {
        FriendSuggestionComponent mFriendSuggestionComponent = DaggerFriendSuggestionComponent.builder()
                .contextModule(new ContextModule(friendSuggestionFragment.getActivity()))
                .build();

        mFriendSuggestionComponent.inject(friendSuggestionFragment);

    }
}
