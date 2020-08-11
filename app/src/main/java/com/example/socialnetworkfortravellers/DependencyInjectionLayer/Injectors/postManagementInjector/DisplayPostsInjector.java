package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DaggerDisplayPostsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DisplayPostsComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules.FragmentActivityModule;
import com.example.socialnetworkfortravellers.ViewLayer.Fragments.postManagementFragments.PostsFragment;

public class DisplayPostsInjector {

    private static DisplayPostsInjector mInjector;

    private DisplayPostsInjector() {
    }

    public static DisplayPostsInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new DisplayPostsInjector();
        }
        return mInjector;
    }


    public void injectIn(PostsFragment postsFragment) {
        DisplayPostsComponent mAddPostComponent = DaggerDisplayPostsComponent.builder()
                .contextModule(new ContextModule(postsFragment.getActivity()))
                .fragmentActivityModule(new FragmentActivityModule(postsFragment.getActivity()))
                .build();

        mAddPostComponent.inject(postsFragment);

    }
}
