package com.nikita.bulygin.weatherapp.data.mappers;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.nikita.bulygin.weatherapp.data.db.DBWeather;
import com.nikita.bulygin.weatherapp.data.network.pojo.DailyWeatherResponse;
import com.nikita.bulygin.weatherapp.data.network.pojo.MainWeatherData;
import com.nikita.bulygin.weatherapp.data.network.pojo.WeatherForDay;
import com.nikita.bulygin.weatherapp.data.network.pojo.WeatherInfo;
import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Mapper from {@link DBWeather} to {@link Weather}
 * and from {@link com.nikita.bulygin.weatherapp.data.network.pojo.DailyWeatherResponse} to {@link DBWeather}
 */
public class WeatherMapper {

    private static final double KELIVN = 273.15;

    @NonNull
    public static Weather map(@NonNull DBWeather dbWeather) {
        return new Weather(new Date(dbWeather.getDate()), dbWeather.getTemp());
    }

    @NonNull
    public static List<DBWeather> map(@NonNull DailyWeatherResponse response, City city) {
        List<WeatherForDay> weatherForDays = response.getWeather();
        if(weatherForDays == null){
            return new ArrayList<>();
        }
        List<DBWeather> result = new ArrayList<>(weatherForDays.size());
        for (WeatherForDay weatherForDay : weatherForDays) {
            MainWeatherData mainData = weatherForDay.getMainWeatherData();
            WeatherInfo weatherInfo = getFirstWeatherInfoFromWeatherDay(weatherForDay);
            DBWeather weather = new DBWeather(city.getId(),
                    weatherForDay.getDateUnix() * 1000L,
                    mainData.getTemp()-KELIVN,
                    mainData.getTempMin()-KELIVN,
                    mainData.getTempMax()-KELIVN,
                    mainData.getPressure(),
                    mainData.getHumidity(),
                    weatherInfo == null ? "" : weatherInfo.getName(),
                    weatherInfo == null ? "" : weatherInfo.getDescription(),
                    weatherInfo == null ? "" : weatherInfo.getIconId());
            result.add(weather);
        }
        return result;
    }

    @Nullable
    private static WeatherInfo getFirstWeatherInfoFromWeatherDay(@NonNull WeatherForDay weatherForDay) {
        List<WeatherInfo> list = weatherForDay.getWeather();
        if (list == null || list.size() == 0) {
            return null;
        }
        return list.get(0);
    }

    /**
     * map db objects tp domain
     *
     * @param dbWeathers database weather
     * @return database weather converted to domain weather
     */
    @NonNull
    public static List<Weather> mapListFromDb(@NonNull List<DBWeather> dbWeathers) {
        List<Weather> result = new ArrayList<>(dbWeathers.size());
        for (DBWeather weather : dbWeathers) {
            result.add(WeatherMapper.map(weather));
        }
        return result;
    }
}
