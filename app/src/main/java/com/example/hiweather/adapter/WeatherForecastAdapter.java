package com.example.hiweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hiweather.R;
import com.example.hiweather.data.Common;
import com.example.hiweather.model.WeatherForecastResult;
import com.squareup.picasso.Picasso;


public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.WeatherForecastViewHolder> {

    public Context context;
    WeatherForecastResult weatherForecastResult;


    public WeatherForecastAdapter(Context context, WeatherForecastResult weatherForecastResult) {
        this.context = context;
        this.weatherForecastResult = weatherForecastResult;
    }

    @NonNull
    @Override
    public WeatherForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = (LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_weather_forecast, parent, false));
        return new WeatherForecastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastViewHolder holder, int position) {

        //Load Icon
        Picasso.get().load("https://openweathermap.org/img/w/" +
                weatherForecastResult.list.get(position).weather.get(0).getIcon() +
                ".png").into(holder.imageWeather);

        holder.textForecastDate
                .setText(new StringBuilder(Common.convertUnixToDate(weatherForecastResult.list.get(position).dt)));
        holder.textForecastDescription
                .setText(new StringBuilder(weatherForecastResult.list.get(position).weather.get(0).getDescription()));

        holder.textTemperature
                .setText(new StringBuilder(String.valueOf(weatherForecastResult.list.get(position).main.getTemp()))
                        .append(" °C"));
    }

    @Override
    public int getItemCount() {
        return weatherForecastResult.list.size();
    }

    public static class WeatherForecastViewHolder extends RecyclerView.ViewHolder {

        TextView textTemperature, textForecastDescription, textForecastDate;
        ImageView imageWeather;

        public WeatherForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            textTemperature = itemView.findViewById(R.id.textTemperature);
            textForecastDescription = itemView.findViewById(R.id.textForecastDescription);
            textForecastDate = itemView.findViewById(R.id.textForecastDate);
            imageWeather = itemView.findViewById(R.id.imageWeather);
        }
    }
}
