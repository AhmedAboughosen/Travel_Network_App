package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules;

import com.example.socialnetworkfortravellers.utilLayer.SendToActivityUtil;
import com.example.socialnetworkfortravellers.utilLayer.interfaces.ISendToActivityUtli;

import dagger.Module;
import dagger.Provides;

@Module
public class SendActivityModule {

    @Provides
    ISendToActivityUtli ProvidesSendToActivityUtil() {
        return SendToActivityUtil.getInstance();
    }
}
