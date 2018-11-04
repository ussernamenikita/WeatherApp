package com.nikita.bulygin.weatherapp.di.modules;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.nikita.bulygin.weatherapp.BuildConfig;
import com.nikita.bulygin.weatherapp.data.CityRepositoryImpl;
import com.nikita.bulygin.weatherapp.data.WeatherRepositoryImpl;
import com.nikita.bulygin.weatherapp.di.DIConstants;
import com.nikita.bulygin.weatherapp.domain.ICityRepository;
import com.nikita.bulygin.weatherapp.domain.IWeatherInteractor;
import com.nikita.bulygin.weatherapp.domain.IWeatherRepository;
import com.nikita.bulygin.weatherapp.domain.WeatherInteractorImpl;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class AppModule {

    @Named(DIConstants.APP)
    @Singleton
    @Binds
    public abstract Context getAppContext(Application application);

    @Singleton
    @Binds
    public abstract IWeatherRepository getWeatherRepository(WeatherRepositoryImpl impl);

    @Singleton
    @Binds
    public abstract ICityRepository getCityRepository(CityRepositoryImpl impl);

    @Singleton
    @Binds
    public abstract IWeatherInteractor getWeatherInteractor(WeatherInteractorImpl impl);

    @Singleton
    @Provides
    @Named(DIConstants.WEATHER_API_KEY)
    public static String getApiKey(){
        return BuildConfig.weatherApiKey;
    }

    @Named(DIConstants.SERVER_PATH)
    @Singleton
    @Provides
    public static String getServerPath(){
        return BuildConfig.path;
    }

    @Provides
    public static Gson getGson(){
        return new Gson();
    }
}
