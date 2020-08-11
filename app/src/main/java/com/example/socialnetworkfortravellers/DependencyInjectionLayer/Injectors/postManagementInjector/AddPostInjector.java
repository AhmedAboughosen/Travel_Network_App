package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Injectors.postManagementInjector;

import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.AddPostComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Components.postManagementComponents.DaggerAddPostComponent;
import com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.ContextModule;
import com.example.socialnetworkfortravellers.ViewLayer.Activties.postManagementActivities.AddPostActivity;

public class AddPostInjector {


    private static AddPostInjector mInjector;

    private AddPostInjector() {
    }

    public static AddPostInjector getSharedInjector() {
        if (mInjector == null) {
            mInjector = new AddPostInjector();
        }
        return mInjector;
    }


    public void injectIn(AddPostActivity addPostActivity) {
        AddPostComponent mAddPostComponent = DaggerAddPostComponent.builder()
                .contextModule(new ContextModule(addPostActivity))
                .build();

        mAddPostComponent.inject(addPostActivity);

    }
}
