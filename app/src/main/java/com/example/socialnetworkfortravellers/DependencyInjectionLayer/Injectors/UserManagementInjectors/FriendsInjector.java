package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerFriendsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerProfilePictureComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.FriendsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.FriendsFragment;

public class FriendsInjector {

    private static FriendsInjector mInjector;

    private FriendsInjector() {
    }

    public static FriendsInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new FriendsInjector();
        }
        return mInjector;
    }


    public void injectIn(FriendsFragment friendsFragment) {
        FriendsComponent mFriendsComponent = DaggerFriendsComponent.builder().contextModule(new ContextModule(friendsFragment.getActivity())).build();
        mFriendsComponent.inject(friendsFragment);

    }
}
