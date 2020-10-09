package com.example.hiweather.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.hiweather.R;
import com.example.hiweather.data.APIClient;
import com.example.hiweather.data.Common;
import com.example.hiweather.data.IOpenWeatherMap;
import com.example.hiweather.model.WeatherResult;
import com.squareup.picasso.Picasso;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;


public class TodayWeatherFragment extends Fragment {

    ImageView imageWeather;
    TextView textCityName, textTemperature, textDescription, textDataTime, textWind,
             textPressure, textHumidity, textSunrise, textSunset,textGeoCoord, loadingText;

    Button okBtn, loadingBtn;

    ProgressBar loadingBar;

    LinearLayout loading;
    LinearLayout weatherPanel;

    CompositeDisposable compositeDisposable;
    IOpenWeatherMap mService;

    static TodayWeatherFragment instance;

    WeatherResult weatherResult;

    public TodayWeatherFragment()
    {
        compositeDisposable = new CompositeDisposable();
        Retrofit retrofit = APIClient.getInstance();
        mService = retrofit.create(IOpenWeatherMap.class);
    }

    public static TodayWeatherFragment getInstance()
    {
        if (instance == null)
        {
            instance = new TodayWeatherFragment();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_today_weather, container, false);
        setUpView(view);
        return view;
    }

    @Override
    public void onStop()
    {
        compositeDisposable.clear();
        super.onStop();
    }

    private void setUpView(View view)
    {
        imageWeather = view.findViewById(R.id.imageWeather);
        textCityName = view.findViewById(R.id.textCityName);
        textTemperature = view.findViewById(R.id.textTemperature);
        textDescription = view.findViewById(R.id.textDescription);
        textWind = view.findViewById(R.id.textWind);
        textDataTime = view.findViewById(R.id.textDataTime);
        textPressure = view.findViewById(R.id.textPressure);
        textHumidity = view.findViewById(R.id.textHumidity);
        textSunrise = view.findViewById(R.id.textSunrise);
        textSunset = view.findViewById(R.id.textSunset);
        textGeoCoord = view.findViewById(R.id.textGeoCoord);

        loadingBar = view.findViewById(R.id.loadingBar);
        loadingBtn = view.findViewById(R.id.loadingBtn);
        loadingText = view.findViewById(R.id.loadingText);

        okBtn = view.findViewById(R.id.okBtn);
        loading = view.findViewById(R.id.loading);
        weatherPanel = view.findViewById(R.id.weatherPanel);

        weatherPanel.setVisibility(View.INVISIBLE);
        loading.setVisibility(View.VISIBLE);

        loadingBtn.setOnClickListener(v -> {
                   System.exit(0);
        });
        okBtn.setOnClickListener(v -> {
              System.exit(0);
        });

        getWeatherInfo();
    }

    private void getWeatherInfo()
    {
        compositeDisposable.add(mService.getWeatherByLatLng(
                String.valueOf(Common.current_location.getLatitude()),
                String.valueOf(Common.current_location.getLongitude()),
                Common.APP_ID,
                "metric")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(weatherResult -> {
                    //Load Image
                    Picasso.get().load("https://openweathermap.org/img/w/" +
                            weatherResult.getWeather().get(0).getIcon() +
                            ".png").into(imageWeather);

                    //Load Information
                    textCityName.setText(weatherResult.getName());

                    textDescription.setText(new StringBuilder(weatherResult.getWeather().get(0).getDescription()));

                    textTemperature.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getTemp()))
                        .append(" °C"));

                    textDataTime.setText(Common.convertUnixToDate(weatherResult.getDt()));

                    textWind.setText(new StringBuilder(String.valueOf(weatherResult.getWind().getSpeed()))
                        .append(" Speed"));

                    textPressure.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getPressure()))
                        .append(" hpa"));

                    textHumidity.setText(new StringBuilder(String.valueOf(weatherResult.getMain().getHumidity()))
                        .append(" %"));

                    textSunrise.setText(Common.convertUnixToHour(weatherResult.getSys().getSunrise()));

                    textSunset.setText(Common.convertUnixToHour(weatherResult.getSys().getSunset()));

                    textGeoCoord.setText(weatherResult.getCoord().toString());

                    //Display panel
                    weatherPanel.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);

                }, throwable ->

                        Toast.makeText(getActivity(), "Check Your Internet Connection", Toast.LENGTH_SHORT).show()));

    }
}