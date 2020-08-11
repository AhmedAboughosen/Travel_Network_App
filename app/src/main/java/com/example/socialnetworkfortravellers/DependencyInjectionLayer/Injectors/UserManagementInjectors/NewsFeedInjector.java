package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.UserManagementInjectors;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.DaggerNewsFeedComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.UserManagementComponents.NewsFeedComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.NewsFeedFragment;

public class NewsFeedInjector {
    private static NewsFeedInjector mInjector;

    private NewsFeedInjector() {
    }

    public static NewsFeedInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new NewsFeedInjector();
        }
        return mInjector;
    }


    public void injectIn(NewsFeedFragment newsFeedFragment) {
        NewsFeedComponent newsFeedComponent = DaggerNewsFeedComponent.builder().contextModule(new ContextModule(newsFeedFragment.getActivity())).build();
        newsFeedComponent.inject(newsFeedFragment);

    }
}
