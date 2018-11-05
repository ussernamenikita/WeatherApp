package com.nikita.bulygin.weatherapp.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.nikita.bulygin.weatherapp.R;
import com.nikita.bulygin.weatherapp.databinding.ListItemWeatherDayBinding;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;
import com.nikita.bulygin.weatherapp.utils.ContextUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Holder for weather item
 */

public class WeatherViewHolder extends RecyclerView.ViewHolder {

    private final ContextUtils contextUtils;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMM", Locale.ENGLISH);
    private final ListItemWeatherDayBinding binding;
    @Nullable
    private Weather weather;

    public WeatherViewHolder(@NonNull ListItemWeatherDayBinding binding, ContextUtils contextUtils) {
        super(binding.getRoot());
        this.binding = binding;
        this.contextUtils = contextUtils;
    }

    public void showWeather(@NonNull Weather weather){
        this.weather = weather;
        updateUI();
    }

    private void updateUI() {
        String date = weather == null ? "" : simpleDateFormat.format(weather.getDate());
        String temp = weather == null ? "" : String.format(Locale.ENGLISH, "%.2f", weather.getTemp());
        binding.dateValueTv.setText(date);
        binding.temparatureValueTv.setText(contextUtils.getString(R.string.temperature_in_celsius_template,temp));
    }


}
