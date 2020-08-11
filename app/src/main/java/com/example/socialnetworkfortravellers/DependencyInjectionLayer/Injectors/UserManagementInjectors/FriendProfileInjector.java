package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerFriendProfileComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.FriendProfileComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.profileFragments.FriendProfileFragment;

public class FriendProfileInjector {

    private static FriendProfileInjector mInjector;

    private FriendProfileInjector() {
    }

    public static FriendProfileInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new FriendProfileInjector();
        }
        return mInjector;
    }


    public void injectIn(FriendProfileFragment friendProfileFragment) {
        FriendProfileComponent mFriendProfileComponent = DaggerFriendProfileComponent.builder()
                .contextModule(new ContextModule(friendProfileFragment.getActivity()))
                .build();

        mFriendProfileComponent.inject(friendProfileFragment);

    }
}
