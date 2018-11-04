package com.nikita.bulygin.weatherapp.di.modules;


import com.nikita.bulygin.weatherapp.data.network.api.WeatherApi;
import com.nikita.bulygin.weatherapp.di.DIConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class ApiModule {

    @Provides
    @Singleton
    public static WeatherApi getWeatherApi(@Named(DIConstants.SERVER_PATH) String path){
        return new Retrofit.
                Builder().
                baseUrl(path).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build().
                create(WeatherApi.class);
    }
}
