package com.nikita.bulygin.weatherapp.di.components;

import com.nikita.bulygin.weatherapp.di.FragmentScope;
import com.nikita.bulygin.weatherapp.di.modules.WeatherByCityModule;
import com.nikita.bulygin.weatherapp.ui.WeatherFragment;

import dagger.Subcomponent;

@FragmentScope
@Subcomponent(modules = WeatherByCityModule.class)
public interface WeatherByCityComponent {

    void inject(WeatherFragment fragment);


}
