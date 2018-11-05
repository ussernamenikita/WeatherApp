package com.nikita.bulygin.weatherapp.di.modules;


import android.app.Activity;
import android.view.LayoutInflater;

import com.nikita.bulygin.weatherapp.di.ActivityScope;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    @Provides
    @ActivityScope
    public static LayoutInflater getLayoutInflater(Activity activity){
        return LayoutInflater.from(activity);
    }
}
