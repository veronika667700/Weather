package com.example.user.finish_project.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.finish_project.DBManager;
import com.example.user.finish_project.FormatWeather;
import com.example.user.finish_project.R;
import com.example.user.finish_project.Weather;

public class DetailFragment extends Fragment {
    private Context context;
    private DBManager dbManager;
    private Weather weather;

    ImageView imageViewDescription;
    TextView textViewTemperature;
    TextView textViewDate;
    TextView textViewDescription;
    TextView textViewWind;
    TextView textViewPressure;
    TextView textViewHumidity;

    public void getWeather(Weather weather) {
        this.weather = weather;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        context = getActivity();
        dbManager = new DBManager(context);

        rootView = viewInit(rootView);

        if (weather != null) {
            textViewTemperature.setText(FormatWeather
                    .temperatureToString(weather.getTemperature().getMaxTemperature()));
            textViewDate.setText(FormatWeather.getDate(weather.getDay()));
            textViewDescription.setText(weather.getDescription());
            textViewWind.setText(FormatWeather.getWindSpeed(weather));
            textViewPressure.setText(FormatWeather.getPressure(weather));
            textViewHumidity.setText(FormatWeather.getHumidity(weather));
            imageViewDescription.setImageResource(FormatWeather.getWeatherIcon(weather));
        }

        return rootView;
    }

    private View viewInit(View rootView) {
        imageViewDescription = (ImageView) rootView.findViewById(R.id.imageViewDetailDescription);
        textViewTemperature = (TextView) rootView.findViewById(R.id.textViewDetailTemperature);
        textViewDate = (TextView) rootView.findViewById(R.id.textViewDetailDate);
        textViewDescription = (TextView) rootView.findViewById(R.id.textViewDetailDescription);
        textViewWind = (TextView) rootView.findViewById(R.id.textViewDetailWind);
        textViewPressure = (TextView) rootView.findViewById(R.id.textViewDetailPressure);
        textViewHumidity = (TextView) rootView.findViewById(R.id.textViewDetailHumidity);
        return  rootView;
    }
}