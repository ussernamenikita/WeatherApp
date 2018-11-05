package com.nikita.bulygin.weatherapp.di.modules;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;

import com.nikita.bulygin.weatherapp.R;
import com.nikita.bulygin.weatherapp.databinding.FragmentWeatherByCityBinding;
import com.nikita.bulygin.weatherapp.di.FragmentScope;
import com.nikita.bulygin.weatherapp.di.WeatherViewModelFactory;
import com.nikita.bulygin.weatherapp.ui.CitesArrayAdapter;
import com.nikita.bulygin.weatherapp.vm.IWeatherViewModel;
import com.nikita.bulygin.weatherapp.vm.WeatherViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class WeatherByCityModule {

    private Fragment fragment;

    public WeatherByCityModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Fragment getFragment(){
        return fragment;
    }

    @FragmentScope
    @Provides
    public static IWeatherViewModel weatherViewModel(Fragment fragment, WeatherViewModelFactory factory){
        return ViewModelProviders.of(fragment,factory).get(WeatherViewModel.class);
    }

    @FragmentScope
    @Provides
    public static FragmentWeatherByCityBinding getFragmentWeatherByCityBinding(LayoutInflater inflater){
        return FragmentWeatherByCityBinding.inflate(inflater);
    }

    @Provides
    public static CitesArrayAdapter getCityArrayAdapter(Context context){
        return new CitesArrayAdapter(context, R.layout.drop_down_item_city);
    }
}
