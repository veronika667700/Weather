package com.example.user.finish_project.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.finish_project.FormatWeather;
import com.example.user.finish_project.R;
import com.example.user.finish_project.Weather;

import java.util.ArrayList;

public class WeatherAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Weather> weatherForecastList;

    public WeatherAdapter(Context context, ArrayList<Weather> weatherForecastList) {
        this.context = context;
        this.weatherForecastList = weatherForecastList;
    }

    @Override
    public int getCount() {
        return weatherForecastList.size();
    }

    @Override
    public Weather getItem(int position) {
        return weatherForecastList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        // если ничего еще не было создано
        if (convertView == null) {
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_weather_fragment, parent, false);
            viewHolder = viewHolderInit(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textViewMaxTemperature.setText(FormatWeather
                .temperatureToString(getItem(position).getTemperature().getMaxTemperature()));

        viewHolder.textViewMinTemperature.setText(FormatWeather
                .temperatureToString(getItem(position).getTemperature().getMinTemperature()));

        viewHolder.imageViewDescription.setImageResource(FormatWeather
                .getWeatherIcon(getItem(position)));

        viewHolder.textViewDate.setText(FormatWeather.getDate(getItem(position).getDay()));

        return convertView;
    }

    private ViewHolder viewHolderInit(View convertView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.textViewMaxTemperature = (TextView) convertView
                .findViewById(R.id.textViewWFMaxTemperature);

        viewHolder.textViewMinTemperature = (TextView) convertView
                .findViewById(R.id.textViewWFMinTemperature);

        viewHolder.imageViewDescription = (ImageView) convertView
                .findViewById(R.id.imageViewWFDescription);

        viewHolder.textViewDate = (TextView) convertView
                .findViewById(R.id.textViewWFDate);

        return viewHolder;
    }

    static class ViewHolder {
        TextView textViewDate;
        TextView textViewMaxTemperature;
        TextView textViewMinTemperature;
        ImageView imageViewDescription;
    }

}
