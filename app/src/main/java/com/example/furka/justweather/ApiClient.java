package com.example.furka.justweather;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    public static final String mainUrl="https://api.openweathermap.org/data/2.5/";
    public static final String apiKey ="70519525643fea9551d7682ce4a2efa5";
    public static final String iconUrl="http://openweathermap.org/img/w/";

    private static Retrofit myRetrofit=null;

    public static Retrofit getMyRetrofit(String mainUrl){

        if (myRetrofit==null){

            myRetrofit=new Retrofit.Builder()
                    .baseUrl(mainUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }return myRetrofit;
    }
}
