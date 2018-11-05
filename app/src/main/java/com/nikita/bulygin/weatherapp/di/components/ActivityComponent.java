package com.nikita.bulygin.weatherapp.di.components;

import com.nikita.bulygin.weatherapp.di.ActivityScope;
import com.nikita.bulygin.weatherapp.di.modules.ActivityModule;
import com.nikita.bulygin.weatherapp.di.modules.WeatherByCityModule;

import dagger.Subcomponent;


@Subcomponent(modules = ActivityModule.class)
@ActivityScope
public interface ActivityComponent {

    WeatherByCityComponent getWeatherByCityComponent(WeatherByCityModule module);
}
