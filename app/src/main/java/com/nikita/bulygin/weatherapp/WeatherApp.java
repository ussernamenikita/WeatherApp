package com.nikita.bulygin.weatherapp;

import android.support.annotation.Nullable;

import com.nikita.bulygin.weatherapp.di.components.AppComponent;
import com.nikita.bulygin.weatherapp.di.components.DaggerAppComponent;

public class WeatherApp extends android.app.Application {

    @Nullable
    private AppComponent appComponent = null;


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
}
