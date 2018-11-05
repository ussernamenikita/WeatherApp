package com.nikita.bulygin.weatherapp.domain;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Single;


public class WeatherInteractorImpl implements IWeatherInteractor {

    private IWeatherRepository weatherRepository;
    private ICityRepository cityRepository;

    @Inject
    public WeatherInteractorImpl(IWeatherRepository repository, ICityRepository cityRepository) {
        this.weatherRepository = repository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Single<DomainResponse<List<Weather>>> getWeather(City city) {
        return weatherRepository.
                getWeatherFromCity(city).
                map(this::groupByDays);
    }


    /**
     * Group all weather by day to one weather object.
     * Do it for every day.
     * @param response response with all weather
     * @return response with grouped result
     */
    @NonNull
    @VisibleForTesting
    public DomainResponse<List<Weather>> groupByDays(@NonNull DomainResponse<List<Weather>> response) {
        List<Weather> result;
        if (response.getData() != null) {
            List<Weather> weathers = response.getData();
            result = new ArrayList<>(weathers.size());
            Calendar calendar = new GregorianCalendar();
            Calendar averageDate = new GregorianCalendar();
            Map<Date, Pair<Integer, Double>> accumulateTempByDays = new HashMap<>();
            for (Weather w : weathers) {
                calendar.setTime(w.getDate());
                averageDate.set(Calendar.YEAR, calendar.get(Calendar.YEAR));
                averageDate.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
                averageDate.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
                Pair<Integer, Double> values = getOrCreate(averageDate.getTime(), accumulateTempByDays);
                values.first++;
                values.second += w.getTemp();
            }
            for (Date date : accumulateTempByDays.keySet()) {
                Pair<Integer, Double> p = accumulateTempByDays.get(date);
                result.add(new Weather(date, p.second / p.first));
            }
        } else {
            result = new ArrayList<>();
        }
        return new DomainResponse<>(result, response.getErrorCode(), response.getStatus());
    }

    /**
     * Return value with existing key
     * or create new one.
     *
     * @param time                key
     * @param allTemperatureInDay map
     * @return existing or new value
     */
    @NonNull
    private Pair<Integer, Double> getOrCreate(@NonNull Date time, @NonNull Map<Date, Pair<Integer, Double>> allTemperatureInDay) {
        Pair<Integer, Double> value = allTemperatureInDay.get(time);
        if (value == null) {
            value = new Pair<>(0, 0d);
            allTemperatureInDay.put(time, value);
        }
        return value;
    }

    @Override
    public Single<DomainResponse<List<City>>> getCitiesForPrefix(String prefix) {
        return cityRepository
                .getPossibleCities(prefix);
    }


    public static class Pair<T, E> {
        T first = null;
        E second = null;

        Pair(T first, E second) {
            this.first = first;
            this.second = second;
        }
    }
}
