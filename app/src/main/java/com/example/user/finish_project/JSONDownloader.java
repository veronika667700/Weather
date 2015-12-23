package com.example.user.finish_project;

import android.util.Log;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JSONDownloader {
    public static String downloadJSON(String city, String metric, String lang) {
        String result = null;
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?" +
                    "q=" + URLEncoder.encode(city, "UTF-8") +
                    "&units=" + metric +
                    "&lang=" + lang +
                    "&type=" + "accurate" +
                    "&cnt=" + "14" +
                    "&appid=" + "0408201c1a78c86fd9433b7efbc51b4f");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000 /* milliseconds */);
            connection.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
            reader.close();
            connection.disconnect();
            result = builder.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d(ResourceHelper.getString(R.string.log_tag), "Downloading error\n" + e.toString());
        }
        return result;
    }
}
