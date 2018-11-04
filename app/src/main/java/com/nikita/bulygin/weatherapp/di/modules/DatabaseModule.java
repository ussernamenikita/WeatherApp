package com.nikita.bulygin.weatherapp.di.modules;


import android.arch.persistence.room.Room;
import android.content.Context;

import com.nikita.bulygin.weatherapp.data.db.CityDao;
import com.nikita.bulygin.weatherapp.data.db.Database;
import com.nikita.bulygin.weatherapp.data.db.WeatherDao;
import com.nikita.bulygin.weatherapp.di.DIConstants;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @Named(DIConstants.APP)
    @Singleton
    public static Database getDatabase(Context application) {
        return Room.
                databaseBuilder(application, Database.class, "Database").
                fallbackToDestructiveMigration().
                build();
    }

    @Provides
    public static WeatherDao getWeatherDao(Database database){
        return database.getWeatherDao();
    }
    @Provides
    public static CityDao getCityDao(Database database){
        return database.getCityDao();
    }


}
