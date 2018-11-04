package com.nikita.bulygin.weatherapp.di.modules;


import com.nikita.bulygin.weatherapp.di.DIConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

@Module
public class SchedulersModule {

    @Provides
    @Named(DIConstants.IO)
    @Singleton
    public Scheduler getIOScheduler(){
        return Schedulers.io();
    }

    @Provides
    @Named(DIConstants.NETWORK)
    @Singleton
    public Scheduler getNetworkScheduler(){
        return Schedulers.io();
    }


}
