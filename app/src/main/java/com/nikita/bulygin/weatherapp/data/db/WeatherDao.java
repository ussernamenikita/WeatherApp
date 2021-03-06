package com.nikita.bulygin.weatherapp.data.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;

@Dao
public abstract class WeatherDao {


    @Query("SELECT * from DBWeather WHERE cityId = :cityId_ ORDER BY date ASC")
    public abstract List<DBWeather> getWeatherByCityBlocking(int cityId_);

    @Query("SELECT * from DBWeather WHERE cityId = :cityId_ ORDER BY date ASC")
    public abstract Single<List<DBWeather>> getWeatherByCity(int cityId_);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(List<DBWeather> weathers);

    @Query("DELETE from DBWeather WHERE date < :l")
    public abstract void deleteOlderThan(long l);
}
