package com.nikita.bulygin.weatherapp.data;

import com.nikita.bulygin.weatherapp.data.db.DBWeather;
import com.nikita.bulygin.weatherapp.data.db.WeatherDao;
import com.nikita.bulygin.weatherapp.data.mappers.WeatherMapper;
import com.nikita.bulygin.weatherapp.data.network.api.WeatherApi;
import com.nikita.bulygin.weatherapp.di.DIConstants;
import com.nikita.bulygin.weatherapp.domain.DomainResponse;
import com.nikita.bulygin.weatherapp.domain.ErrorCodes;
import com.nikita.bulygin.weatherapp.domain.IWeatherRepository;
import com.nikita.bulygin.weatherapp.domain.entities.City;
import com.nikita.bulygin.weatherapp.domain.entities.Weather;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.SingleSubject;


public class WeatherRepositoryImpl implements IWeatherRepository {

    private final String appId;
    private WeatherApi api;
    private Scheduler NET;
    private WeatherDao weatherDao;
    private HashMap<City, Disposable> disposableHashMap = new HashMap<>();
    private Map<City, WeakReference<Single<DomainResponse<List<Weather>>>>> lastRequestsResponse = new HashMap<>();

    @Inject
    public WeatherRepositoryImpl(WeatherApi api,
                                 @Named(DIConstants.NETWORK) Scheduler NET,
                                 WeatherDao weatherDao,
                                 @Named(DIConstants.WEATHER_API_KEY) String appId) {
        this.api = api;
        this.NET = NET;
        this.weatherDao = weatherDao;
        this.appId = appId;
        this.clearOldData();
    }

    private void clearOldData() {
        NET.scheduleDirect(() -> weatherDao.deleteOlderThan(System.currentTimeMillis()));
    }

    @Override
    public Single<DomainResponse<List<Weather>>> getWeatherFromCity(City city) {
        //check if request with this city already executed
        if (isDisposed(city)) {
            //remove old request if exists
            lastRequestsResponse.remove(city);
            Single<List<DBWeather>> requestFromNetwork = updateFromNetwork(city);
            Single<DomainResponse<List<Weather>>> result = requestFromNetwork.
                    map(list -> {
                        weatherDao.insert(list);
                        List<Weather> resultList = WeatherMapper.mapListFromDb(list);
                        return new DomainResponse<>(resultList, ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS);
                    }).onErrorReturn(throwable -> {
                List<Weather> list = WeatherMapper.mapListFromDb(weatherDao.getWeatherByCityBlocking(city.getId()));
                return new DomainResponse<>(list, ErrorCodes.NETWORK_ERROR, DomainResponse.RESULT_STATUS.ERROR);
            });
            //cache request
            lastRequestsResponse.put(city, new WeakReference<>(result));
            return result;
        } else {
            //try to get cached value of last response
            WeakReference<Single<DomainResponse<List<Weather>>>> lastReference = lastRequestsResponse.get(city);
            Single<DomainResponse<List<Weather>>> result = lastReference == null ? null : lastReference.get();
            return result == null ? weatherDao.
                    getWeatherByCity(city.getId()).
                    subscribeOn(NET).
                    map(WeatherMapper::mapListFromDb).
                    map(list -> new DomainResponse<>(list, ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS)) : result;
        }
    }


    /**
     * Call request from network immediately
     * and return observable with it's data
     *
     * @param city request weather city
     * @return observable with weather in city {@code city}
     */
    private Single<List<DBWeather>> updateFromNetwork(City city) {
        SingleSubject<List<DBWeather>> subject = SingleSubject.create();
        Disposable disposable = api.getWeather5_3(city.getId(), appId)
                .subscribeOn(NET)
                .map(response -> WeatherMapper.map(response, city))
                .subscribe(subject::onSuccess, subject::onError);
        disposableHashMap.put(city, disposable);
        return subject;
    }

    private boolean isDisposed(City city) {
        Disposable disposable = disposableHashMap.get(city);
        return disposable == null || disposable.isDisposed();
    }


}
