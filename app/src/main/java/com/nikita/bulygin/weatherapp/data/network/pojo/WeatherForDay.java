package com.nikita.bulygin.weatherapp.data.network.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForDay {

    @SerializedName("dt")
    @Expose
    private Integer dateUnix;
    @SerializedName("main")
    @Expose
    private MainWeatherData mainWeatherData;
    @SerializedName("weather")
    @Expose
    private List<WeatherInfo> weather = null;

    public Integer getDateUnix() {
        return dateUnix;
    }

    public void setDateUnix(Integer dateUnix) {
        this.dateUnix = dateUnix;
    }

    public MainWeatherData getMainWeatherData() {
        return mainWeatherData;
    }

    public void setMainWeatherData(MainWeatherData mainWeatherData) {
        this.mainWeatherData = mainWeatherData;
    }

    public List<WeatherInfo> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherInfo> weather) {
        this.weather = weather;
    }
}