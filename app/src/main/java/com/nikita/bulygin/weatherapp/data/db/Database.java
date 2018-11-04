package com.nikita.bulygin.weatherapp.data.db;


import android.arch.persistence.room.RoomDatabase;

@android.arch.persistence.room.Database(entities = {DBCity.class,DBWeather.class},version = 6,exportSchema = false)
public abstract class Database extends RoomDatabase {

    public abstract WeatherDao getWeatherDao();
    public abstract CityDao getCityDao();
}
