package com.nikita.bulygin.weatherapp.data;

import android.content.Context;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.nikita.bulygin.weatherapp.data.db.CityDao;
import com.nikita.bulygin.weatherapp.data.mappers.CityMapper;
import com.nikita.bulygin.weatherapp.di.DIConstants;
import com.nikita.bulygin.weatherapp.domain.DomainResponse;
import com.nikita.bulygin.weatherapp.domain.ErrorCodes;
import com.nikita.bulygin.weatherapp.domain.ICityRepository;
import com.nikita.bulygin.weatherapp.domain.entities.City;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Scheduler;
import io.reactivex.Single;


public class CityRepositoryImpl implements ICityRepository {

    private static final String TAG = "CityRepositoryImpl";
    private final Scheduler IOscheDuler;

    private CityDao cityDao;

    @Inject
    public CityRepositoryImpl(CityDao cityDao,
                              @Named(DIConstants.APP) Context context,
                              Gson gson,
                              @Named(DIConstants.IO) Scheduler scheduler) {
        this.cityDao = cityDao;
        this.IOscheDuler = scheduler;
        scheduler.createWorker().schedule(() -> populateCities(context, gson));
    }

    @WorkerThread
    private void populateCities(Context context, Gson gson) {
        int count = cityDao.getCityCount();
        if (count > 0) {
            return;
        }
        InputStream is = null;
        try {
            is = context.getAssets().open("city.list.json");
            JsonReader jsonReader = new JsonReader(new InputStreamReader(is));
            TypeToken token = new TypeToken<List<com.nikita.bulygin.weatherapp.data.network.pojo.City>>() {
            };
            List<com.nikita.bulygin.weatherapp.data.network.pojo.City> result = gson.fromJson(jsonReader, token.getType());
            cityDao.insertCities(CityMapper.toArrayDBCitiesFromPojo(result));
        } catch (IOException ex) {
            Log.d(TAG, "populateCities: error while try read file with cities");
            ex.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    Log.d(TAG, "populateCities: Error while try close strean with city json file");
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public Single<DomainResponse<List<City>>> getPossibleCities(String prefix) {
        return cityDao.
                getCityWithPrefix(prefix).
                subscribeOn(IOscheDuler).
                map(CityMapper::fromArrayDBCities).
                map(cities -> new DomainResponse<>(cities, ErrorCodes.NO_ERROR, DomainResponse.RESULT_STATUS.SUCCESS));
    }
}
