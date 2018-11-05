package com.nikita.bulygin.weatherapp.di.components;

import android.support.v4.app.Fragment;

import com.nikita.bulygin.weatherapp.di.FragmentScope;
import com.nikita.bulygin.weatherapp.di.modules.WeatherByCityModule;

import dagger.BindsInstance;
import dagger.Component;

@FragmentScope
@Component(dependencies = ActivityComponent.class, modules = WeatherByCityModule.class)
public interface WeatherByCityComponent {

    @Component.Builder
    interface Builder {
        WeatherByCityComponent build();

        @BindsInstance
        WeatherByCityComponent.Builder fragment(Fragment fragment);

        WeatherByCityComponent.Builder activityComponent(ActivityComponent component);
    }
}
