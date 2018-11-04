
package com.nikita.bulygin.weatherapp.data.network.pojo;

import android.support.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DailyWeatherResponse {

    @SerializedName("list")
    @Expose
    @Nullable
    private List<WeatherForDay> weather;

    @Nullable
    public List<WeatherForDay> getWeather() {
        return weather;
    }

    public void setWeather(@Nullable List<WeatherForDay> weather) {
        this.weather = weather;
    }
}
