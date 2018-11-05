package com.nikita.bulygin.weatherapp.ui;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nikita.bulygin.weatherapp.databinding.ListItemWeatherDayBinding;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;
import com.nikita.bulygin.weatherapp.utils.ContextUtils;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class WeatherRecyclerViewAdapter extends RecyclerView.Adapter<WeatherViewHolder> {

    @NonNull
    private final ContextUtils contextUtils;
    @NonNull
    private List<Weather> list = new ArrayList<>();

    @NonNull
    public List<Weather> getList() {
        return list;
    }

    public void setList(@NonNull List<Weather> list) {
        this.list = list;
    }

    @NonNull
    private LayoutInflater inflater;

    @Inject
    public WeatherRecyclerViewAdapter(@NonNull LayoutInflater inflater,@NonNull ContextUtils contextUtils) {
        this.inflater = inflater;
        this.contextUtils = contextUtils;
    }

    @NonNull
    @Override
    public WeatherViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new WeatherViewHolder(ListItemWeatherDayBinding.inflate(inflater,viewGroup,false),contextUtils);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherViewHolder weatherViewHolder, int i) {
            weatherViewHolder.showWeather(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
