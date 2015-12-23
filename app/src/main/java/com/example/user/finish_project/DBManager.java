package com.example.user.finish_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBManager extends SQLiteOpenHelper {
    private Context context;

    private String tableName;
    private String day;
    private String minTemperature;
    private String maxTemperature;
    private String icon;
    private String description;
    private String windSpeed;
    private String windDirection;
    private String pressure;
    private String humidity;


    public DBManager(Context context) {
        super(context, ResourceHelper.getString(R.string.db_name), null, 1);
        this.context = context;
        tableName = ResourceHelper.getString(R.string.db_name);
        setColumnTitles();
    }

    private void setColumnTitles() {
        day = ResourceHelper.getString(R.string.day);
        minTemperature = ResourceHelper.getString(R.string.min_temperature);
        maxTemperature = ResourceHelper.getString(R.string.max_temperature);
        icon = ResourceHelper.getString(R.string.icon);
        description = ResourceHelper.getString(R.string.description);
        windSpeed = ResourceHelper.getString(R.string.wind_speed);
        windDirection = ResourceHelper.getString(R.string.wind_direction);
        pressure = ResourceHelper.getString(R.string.pressure);
        humidity = ResourceHelper.getString(R.string.humidity);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + tableName +
                        "(id integer primary key autoincrement, " +
                        day + " text, " +
                        minTemperature + " integer, " +
                        maxTemperature + " integer, " +
                        icon + " text, " +
                        description + " text, " +
                        windSpeed + " integer, " +
                        windDirection + " integer, " +
                        pressure + " integer, " +
                        humidity + " integer" +
                        ");"
        );
    }

    public void drop() {
        SQLiteDatabase database =  this.getReadableDatabase();
        database.delete(tableName, null, null);
        database.close();
    }

    public Boolean isDBEmpty() {
        Boolean result;
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor c = database.rawQuery("select * from " + tableName, null);
        result = !c.moveToFirst();
        c.close();
        database.close();
        return result;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void downloadWeather() {
        ArrayList<Weather> weatherForecasts;
        WeatherDownloader weatherDownloader = new WeatherDownloader();
        weatherDownloader.execute(context);
        try {
            weatherForecasts = weatherDownloader.get();
            fillDataBase(weatherForecasts);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(ResourceHelper.getString(R.string.log_tag), "Weather array wasn't formed\n"
                    + e.toString());
        }
    }

    private void fillDataBase(ArrayList<Weather> weatherForecasts) {
        SQLiteDatabase database = this.getWritableDatabase();
        for (Weather x : weatherForecasts) {
            ContentValues cv = new ContentValues();

            cv.put(day, x.getDay());
            cv.put(maxTemperature, x.getTemperature().getMaxTemperature());
            cv.put(minTemperature, x.getTemperature().getMinTemperature());
            cv.put(icon, x.getIcon());
            cv.put(description, x.getDescription());
            cv.put(windSpeed, x.getDetail().getWindSpeed());
            cv.put(windDirection, x.getDetail().getWindDirection());
            cv.put(pressure, x.getDetail().getPressure());
            cv.put(humidity, x.getDetail().getHumidity());

            database.insert(tableName, null, cv);
        }
        database.close();
    }

    public ArrayList<Weather> getWeatherForecasts() {
        ArrayList<Weather> weatherForecasts = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();

        Cursor c = database.query(tableName, null, null, null, null, null, null);
        if (c.moveToFirst()) {
            Integer dateColumnIndex = c.getColumnIndex(day);
            Integer minTemperatureColumnIndex = c.getColumnIndex(minTemperature);
            Integer maxTemperatureColumnIndex = c.getColumnIndex(maxTemperature);
            Integer iconColumnIndex = c.getColumnIndex(icon);
            Integer descriptionColumnIndex = c.getColumnIndex(description);
            Integer windSpeedColumnIndex = c.getColumnIndex(windSpeed);
            Integer windDirectionColumnIndex = c.getColumnIndex(windDirection);
            Integer pressureColumnIndex = c.getColumnIndex(pressure);
            Integer humidityColumnIndex = c.getColumnIndex(humidity);
            do {
                String dateValue = c.getString(dateColumnIndex);
                Integer minTemperatureValue = c.getInt(minTemperatureColumnIndex);
                Integer maxTemperatureValue = c.getInt(maxTemperatureColumnIndex);
                String iconValue = c.getString(iconColumnIndex);
                String descriptionValue = c.getString(descriptionColumnIndex);
                Integer windSpeedValue = c.getInt(windSpeedColumnIndex);
                Integer windDirectionValue = c.getInt(windDirectionColumnIndex);
                Integer pressureValue = c.getInt(pressureColumnIndex);
                Integer humidityValue = c.getInt(humidityColumnIndex);
                Temperature temperatureValue =
                        new Temperature(
                                minTemperatureValue,
                                maxTemperatureValue);
                DetailWeather detailValue =
                        new DetailWeather(windSpeedValue,
                                windDirectionValue,
                                pressureValue,
                                humidityValue);
                Weather currentWeatherForecast =
                        new Weather(dateValue,
                                temperatureValue,
                                iconValue,
                                descriptionValue,
                                detailValue);
                weatherForecasts.add(currentWeatherForecast);
            } while (c.moveToNext());
        }
        c.close();
        database.close();
        return weatherForecasts;
    }
}