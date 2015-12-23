package com.example.user.finish_project;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class JSONParser {
    public static ArrayList<Weather> parseJSON(String jsonFile) {
        ArrayList<Weather> weatherForecasts = new ArrayList<>();

        try {
            JSONObject file = new JSONObject(jsonFile);
            JSONArray list = file.getJSONArray("list");

            for (int it = 0; it < 14; ++it) {
                Weather weatherForecast = new Weather();

                JSONObject listElem = list.getJSONObject(it);
                Long dt = listElem.getLong("dt");
                Date date = new Date(dt * 1000L);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyy");
                String formattedDate = simpleDateFormat.format(date);
                weatherForecast.setDay(formattedDate);

                JSONObject temp = listElem.getJSONObject("temp");
                Temperature temperature = new Temperature();
                temperature.setMinTemperature(Math.round(temp.getLong("min")));
                temperature.setMaxTemperature(Math.round(temp.getLong("max")));
                weatherForecast.setTemperature(temperature);

                JSONArray weather = listElem.getJSONArray("weather");
                JSONObject weatherElem = weather.getJSONObject(0);
                weatherForecast.setIcon(weatherElem.getString("icon"));
                weatherForecast.setDescription(weatherElem.getString("description"));

                DetailWeather detail = new DetailWeather();
                detail.setWindSpeed(Math.round(listElem.getLong("speed")));
                detail.setWindDirection(Math.round(listElem.getLong("deg")));
                detail.setPressure(Math.round(listElem.getLong("pressure")));
                detail.setHumidity(Math.round(listElem.getLong("humidity")));
                weatherForecast.setDetail(detail);

                weatherForecasts.add(weatherForecast);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(ResourceHelper.getString(R.string.log_tag), "Parsing error\n" + e.toString());
        }

        return weatherForecasts;
    }
}
