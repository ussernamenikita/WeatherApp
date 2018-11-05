package com.nikita.bulygin.weatherapp.ui;

import android.support.v4.app.Fragment;

import com.nikita.bulygin.weatherapp.databinding.FragmentWeatherByCityBinding;
import com.nikita.bulygin.weatherapp.vm.IWeatherViewModel;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class WeatherFragment extends Fragment implements IWeatherView {

    @Inject
    IWeatherViewModel weatherViewModel;

    @Inject
    FragmentWeatherByCityBinding weatherByCityBinding;

    @Override
    public void updateState(@NotNull WeatherByCityViewState state) {

    }
}
