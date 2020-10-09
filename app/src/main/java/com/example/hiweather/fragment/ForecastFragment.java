package com.example.hiweather.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hiweather.R;
import com.example.hiweather.adapter.WeatherForecastAdapter;
import com.example.hiweather.data.APIClient;
import com.example.hiweather.data.Common;
import com.example.hiweather.data.IOpenWeatherMap;
import com.example.hiweather.model.WeatherForecastResult;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ForecastFragment extends Fragment {

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    TextView textCityName, textGeoCoord;
    RecyclerView recyclerForecast;

     static ForecastFragment instance;

    public static ForecastFragment getInstance() {
        if (instance == null)
            instance = new ForecastFragment();
        return instance;
    }

    public ForecastFragment()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = APIClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forecast, container, false);
        setUpView(view);
        return view;
    }

    private void setUpView(View view)
    {
        textCityName = view.findViewById(R.id.textCityName);
        textGeoCoord = view.findViewById(R.id.textGeoCoord);
        recyclerForecast = view.findViewById(R.id.recyclerForecast);
        recyclerForecast.setHasFixedSize(true);
        recyclerForecast.setLayoutManager(new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        
        getForecastInformation();
    }

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    private void getForecastInformation()
    {
        compositeDisposable.add(mService.getForecastWeatherByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::displayForecastWeather,
                        throwable -> Toast.makeText(getContext(), ""+throwable.getMessage(), Toast.LENGTH_SHORT).show())
        );
    }

    private void displayForecastWeather(WeatherForecastResult weatherForecastResult)
    {

        textCityName.setText(new StringBuilder(weatherForecastResult.city.name));
        textGeoCoord.setText(new StringBuilder(weatherForecastResult.city.coord.toString()));

        WeatherForecastAdapter adapter = new WeatherForecastAdapter(getContext(), weatherForecastResult);
        recyclerForecast.setAdapter(adapter);
    }
}