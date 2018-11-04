package com.nikita.bulygin.weatherapp.data.network.api;


import com.nikita.bulygin.weatherapp.data.network.pojo.DailyWeatherResponse;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {

    String GET_WEATHER_5_DAY__BY_3_HOURS = "forecast";

    @GET(GET_WEATHER_5_DAY__BY_3_HOURS)
    Observable<DailyWeatherResponse> getWeather5_3(@Query("id") Integer id,@Query("appid") String appId);
}
