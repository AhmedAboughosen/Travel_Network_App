package com.example.socialnetworkfortravellers.DependencyInjectionLayer.Modules.baseModules;


import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.IUpdatePathUsingTransactionService;
import com.example.socialnetworkfortravellers.ApplicationLayer.Services.updatePathUsingTransactionServices.UpdatePathUsingTransactionService;

import dagger.Module;
import dagger.Provides;

@Module
public class UpdatePathUsingTransactionModule {

    @Provides
    public IUpdatePathUsingTransactionService ProvidesUpdatePathUsingTransaction() {
        return new UpdatePathUsingTransactionService();
    }

}
