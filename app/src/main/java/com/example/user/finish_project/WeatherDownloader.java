package com.example.user.finish_project;

import android.content.Context;
import android.os.AsyncTask;
import java.util.ArrayList;

public class WeatherDownloader extends AsyncTask<Context, Void, ArrayList<Weather>> {
    private  Context context;

    @Override
    protected ArrayList<Weather> doInBackground(Context... params) {
        context = params[0];
        // PreferenceHelper - класс, который позволяет достать из SharedPreferences нужные даннные.
        // Чтобы не описывать одинаковые функции в каждом классе, где нужно достать что то из SharedPreferences.
        // Используется для правильного запроса, т.е. нужные город и метрика
        String city = PreferenceHelper.getPreference(R.string.city);
        String metric = PreferenceHelper.getPreference(R.string.metric);
        String lang = ResourceHelper.getString(R.string.lang);
        // JSONDownloader - скачивает нужный JSON, в зависимости от того, что задали через PreferenceHelper
        String jsonFile = JSONDownloader.downloadJSON(city, metric, lang);
        // JSONParser - парсит полученный JSON
        ArrayList<Weather> weatherForecasts = JSONParser.parseJSON(jsonFile);
        return weatherForecasts;
    }
}
