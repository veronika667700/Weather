package com.example.user.finish_project;

import android.content.Context;
import android.os.AsyncTask;
import java.util.ArrayList;

public class WeatherDownloader extends AsyncTask<Context, Void, ArrayList<Weather>> {
    private  Context context;

    @Override
    protected ArrayList<Weather> doInBackground(Context... params) {
        context = params[0];
        String city = PreferenceHelper.getPreference(R.string.city);
        String metric = PreferenceHelper.getPreference(R.string.metric);
        String lang = ResourceHelper.getString(R.string.lang);
        String jsonFile = JSONDownloader.downloadJSON(city, metric, lang);
        ArrayList<Weather> weatherForecasts = JSONParser.parseJSON(jsonFile);
        return weatherForecasts;
    }
}
