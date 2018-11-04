package com.nikita.bulygin.weatherapp.data.db;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;


@Dao
public abstract class CityDao {

    @Query("SELECT * from DBCity")
    public abstract Single<List<DBCity>> getAllCities();

    public Single<List<DBCity>> getCityWithPrefix(String prefix) {
        return getCityWithPrefixProtected(prefix + "%");
    }

    @Query("SELECT * from DBCity where name like :s")
    protected abstract Single<List<DBCity>> getCityWithPrefixProtected(String s);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertCities(List<DBCity> cities);

    @Query("SELECT count(*) from DBCity")
    public abstract int getCityCount();
}
