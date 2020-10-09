package com.example.hiweather.data;


import com.example.hiweather.model.Weather;
import com.example.hiweather.model.WeatherResult;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    private static final String BAS_URL = "https://api.openweathermap.org/data/2.5/";
    private static Retrofit instance;

    public static Retrofit getInstance()
    {
        if (instance == null)
        {
            instance = new Retrofit.Builder()
                    .baseUrl(BAS_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return instance;
    }


}
