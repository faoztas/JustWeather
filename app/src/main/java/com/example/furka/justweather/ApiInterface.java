package com.example.furka.justweather;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.furka.justweather.Models.CurrentWeather.CurrentWeather;
import com.example.furka.justweather.Models.WeatherForecast.WeatherForecast;

public interface ApiInterface {
    @GET("weather?")
    Call<CurrentWeather> getWeather(
            @Query("q") String cityName,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("weather?")
    Call<CurrentWeather> getWeather(
            @Query("id") int cityID,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("weather?")
    Call<CurrentWeather> getWeather(
            @Query("lon") double longitude,
            @Query("lat") double latitude,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("forecast?")
    Call<WeatherForecast> getForecast(
            @Query("q") String cityNamee,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("forecast?")
    Call<WeatherForecast> getForecast(
            @Query("id") int cityID,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );

    @GET("forecast?")
    Call<WeatherForecast> getForecast(
            @Query("lon") double longitude,
            @Query("lat") double latitude,
            @Query("units") String units,
            @Query("lang") String lang,
            @Query("appid") String appid
    );
}