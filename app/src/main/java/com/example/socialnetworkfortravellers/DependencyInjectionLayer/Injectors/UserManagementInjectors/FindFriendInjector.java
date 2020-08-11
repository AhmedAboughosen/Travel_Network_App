package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerFindFriendComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.FindFriendComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.UserManagementActivties.findFriendsActivities.FindFriendActivity;

public class FindFriendInjector {

    private static FindFriendInjector mFindFriendInjector;

    private FindFriendInjector() {
    }

    public static FindFriendInjector getSharedInjector() {
        if (mFindFriendInjector == null) {
            mFindFriendInjector = new FindFriendInjector();
        }
        return mFindFriendInjector;
    }


    public void injectIn(FindFriendActivity findFriendActivity) {
        FindFriendComponent mFindFriendComponent = DaggerFindFriendComponent.builder()
                .contextModule(new ContextModule(findFriendActivity))
                .build();
        mFindFriendComponent.inject(findFriendActivity);
    }
}
