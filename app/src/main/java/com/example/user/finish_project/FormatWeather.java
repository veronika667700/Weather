package com.example.user.finish_project;

import android.content.Context;

public class FormatWeather {
    private static Context context;
    public static void setContext(Context newContext) {
        context = newContext;
    }

    public static int getWeatherIcon(Weather weatherForecast) {
        String icon = weatherForecast.getIcon();
        switch (icon.substring(0, 2)) {
            case "01" : return R.drawable.sun;
            case "02" : return R.drawable.sun_cloud;
            case "03" : return R.drawable.cloud;
            case "04" : return R.drawable.light_rain;
            case "09" : return R.drawable.rain;
            case "10" : return R.drawable.hard_rain;
            case "11" : return R.drawable.light;
            case "13" : return R.drawable.snow;
            default   : return R.drawable.sun_cloud;
        }
    }

    public static String temperatureToString(Integer temp) {
        String degrees = null;
        String metric = PreferenceHelper.getPreference(R.string.metric, "");
        String celsiumMetric = ResourceHelper.getString(R.string.celsium);
        String farenheitMetric = ResourceHelper.getString(R.string.farenheit);
        //  equals - проверяет равенство указанного значения ожидаемому, без проверки типов
        if (metric.equals(celsiumMetric)) {
            degrees = "\u2103";
        } else if (metric.equals(farenheitMetric)) {
            degrees = "\u2109";
        } else {
            degrees = "\u2100";
        }
        return (temp >= 0 ? "+" + temp.toString() + degrees : temp.toString() + degrees);
    }

    public static String getDate(String date) {
        StringBuilder builder = new StringBuilder();
        if (date.charAt(0) != '0')
            builder.append(date.charAt(0));
        builder.append(date.charAt(1));
        builder.append(" " + getMonth(date.substring(3, 5)));
        return builder.toString();
    }

    public static String getMonth(String month) {
        String[] months = ResourceHelper.getStringArray(R.array.months);
        switch (month) {
            case "01" : return months[0];
            case "02" : return months[1];
            case "03" : return months[2];
            case "04" : return months[3];
            case "05" : return months[4];
            case "06" : return months[5];
            case "07" : return months[6];
            case "08" : return months[7];
            case "09" : return months[8];
            case "10" : return months[9];
            case "11" : return months[10];
            case "12" : return months[11];
            default   : return "";
        }
    }

    public static String getWindSpeed(Weather weatherForecast) {
        Integer windSpeed = weatherForecast.getDetail().getWindSpeed();
        String units = null;
        String metric = PreferenceHelper.getPreference(R.string.metric, "");
        String celsiumMetric = ResourceHelper.getString(R.string.celsium);
        String farenheitMetric = ResourceHelper.getString(R.string.farenheit);
        if (metric.equals(celsiumMetric)) {
            units = ResourceHelper.getString(R.string.meter_per_second);
        } else if (metric.equals(farenheitMetric)) {
            units = ResourceHelper.getString(R.string.miles_per_hour);
        } else {
            units = "";
        }
        return " " + windSpeed.toString() + " " + units;
    }

    public static String getPressure(Weather weatherForecast) {
        String units = ResourceHelper.getString(R.string.presssure_units);
        Long pressure = weatherForecast.getDetail().getPressure().longValue();
        if (ResourceHelper.getString(R.string.lang).equals("en")) {
            Double pressureRu = pressure * 0.750062;
            pressure = Math.round(pressureRu);
        }
        return " " + pressure.toString() + " " + units;
    }

    public static String getHumidity(Weather weatherForecast) {
        Integer humidity = weatherForecast.getDetail().getHumidity();
        return " " + humidity.toString() + " %";
    }

}