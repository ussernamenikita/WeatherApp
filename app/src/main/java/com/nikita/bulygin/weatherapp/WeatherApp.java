package com.nikita.bulygin.weatherapp;

import android.support.annotation.NonNull;

import com.nikita.bulygin.weatherapp.di.InjectableApplication;
import com.nikita.bulygin.weatherapp.di.components.AppComponent;
import com.nikita.bulygin.weatherapp.di.components.DaggerAppComponent;

public class WeatherApp extends android.app.Application implements InjectableApplication {


    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initiateAppComponent();
    }

    private void initiateAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.
                    builder().
                    application(this).
                    build();
        }
    }


    @NonNull
    @Override
    public AppComponent getAppComponent() {
        return appComponent;
    }
}
