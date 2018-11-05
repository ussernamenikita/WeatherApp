package com.nikita.bulygin.weatherapp.di.components;


import android.app.Application;

import com.nikita.bulygin.weatherapp.di.modules.ActivityModule;
import com.nikita.bulygin.weatherapp.di.modules.ApiModule;
import com.nikita.bulygin.weatherapp.di.modules.AppModule;
import com.nikita.bulygin.weatherapp.di.modules.DatabaseModule;
import com.nikita.bulygin.weatherapp.di.modules.SchedulersModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, DatabaseModule.class, SchedulersModule.class, ApiModule.class})
public interface AppComponent {

    ActivityComponent getActivityComponent(ActivityModule module);

    @Component.Builder
    interface Builder {
        AppComponent build();

        @BindsInstance
        Builder application(Application application);
    }
}
