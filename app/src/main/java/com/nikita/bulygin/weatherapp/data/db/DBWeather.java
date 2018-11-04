package com.nikita.bulygin.weatherapp.data.db;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;

@Entity(primaryKeys = {"date", "cityId"})
public class DBWeather {


    private long date = 0L;

    private int cityId = 0;

    private Double temp;

    private Double tempMin;

    private Double tempMax;

    private Double pressure;

    private Integer humidity;

    private String name;

    private String description;

    private String iconId;

    public DBWeather() {
    }

    @Ignore
    public DBWeather(int city, long date, Double temp, Double tempMin, Double tempMax, Double pressure, Integer humidity, String name, String description, String iconId) {
        this.date = date;
        this.cityId = city;
        this.temp = temp;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
        this.name = name;
        this.description = description;
        this.iconId = iconId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getTempMin() {
        return tempMin;
    }

    public void setTempMin(Double tempMin) {
        this.tempMin = tempMin;
    }

    public Double getTempMax() {
        return tempMax;
    }

    public void setTempMax(Double tempMax) {
        this.tempMax = tempMax;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
