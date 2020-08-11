package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;

import android.content.Context;

import com.example.socialnetworkfortravellers.utilLayer.CompressImageUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ICompressImageUtil;

import dagger.Module;
import dagger.Provides;

@Module
public class CompressImageUtilModule {

    @Provides
    public ICompressImageUtil ProvidesCompressImageUtil(Context context) {
        return new CompressImageUtil(context);
    }

}
