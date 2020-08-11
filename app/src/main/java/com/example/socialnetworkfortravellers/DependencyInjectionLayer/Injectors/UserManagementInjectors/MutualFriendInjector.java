package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerMutualFriendComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.MutualFriendComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.userManagementFragments.findFriendFragments.MutualFriendFragment;

public class MutualFriendInjector {

    private static MutualFriendInjector mInjector;
    private MutualFriendComponent mMutualFriendComponent;

    private MutualFriendInjector() {
    }

    public static MutualFriendInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new MutualFriendInjector();
        }
        return mInjector;
    }


    public void injectIn(MutualFriendFragment mutualFriendFragment) {
        mMutualFriendComponent = DaggerMutualFriendComponent.builder()
                .contextModule(new ContextModule(mutualFriendFragment.getActivity()))
                .build();

        mMutualFriendComponent.inject(mutualFriendFragment);

    }
}
