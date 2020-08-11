package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import androidx.fragment.app.FragmentActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class FragmentActivityModule {


    private FragmentActivity mFragmentActivity;

    public FragmentActivityModule(FragmentActivity fragmentActivity) {
        this.mFragmentActivity = fragmentActivity;
    }

    @Provides
    public FragmentActivity ProvidesFragmentActivity() {
        return mFragmentActivity;
    }
}
