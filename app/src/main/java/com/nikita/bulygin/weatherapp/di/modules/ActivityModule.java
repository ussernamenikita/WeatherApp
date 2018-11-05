package com.nikita.bulygin.weatherapp.di.modules;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;

import com.nikita.bulygin.weatherapp.di.ActivityScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    Activity getActivity(){
        return activity;
    }

    @Provides
    @ActivityScope
    public static LayoutInflater getLayoutInflater(Activity activity){
        return LayoutInflater.from(activity);
    }

    @Provides
    @ActivityScope
    public Context getContext(Activity activity){
        return activity;
    }
}
