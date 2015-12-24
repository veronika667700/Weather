package com.example.user.finish_project.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.user.finish_project.DBManager;
import com.example.user.finish_project.NetworkHelper;
import com.example.user.finish_project.PreferenceHelper;
import com.example.user.finish_project.R;
import com.example.user.finish_project.Weather;
import com.example.user.finish_project.adapters.WeatherAdapter;

public class WeatherFragment extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener {
    private Context context;
    private DBManager dbManager;
    private TextView textViewCity;
    private ListView listView;
    private WeatherAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public interface OnItemPressed {
        void itemPressed(Weather weatherForecast);
    }
    private OnItemPressed listenerItemPressed;

    public void setOnItemPressedListener(OnItemPressed listenerItemPressed) {
        this.listenerItemPressed = listenerItemPressed;
    }

    public void removeOnItemPressedListener() {
        this.listenerItemPressed = null;
    }


    public interface OnRefresh {
        void showAlertDialog(Context context, String title, String message, Boolean status);
    }
    private OnRefresh listenerRefresh;

    public void setOnRefreshListener(OnRefresh listenerRefresh) {
        this.listenerRefresh = listenerRefresh;
    }

    public void removeOnRefreshListener() {
        this.listenerRefresh = null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_fragment, container, false);

        context = getActivity();
        dbManager = new DBManager(context);
        if (dbManager.isDBEmpty())
            dbManager.downloadWeather();


        rootView = initSwipeRefreshLayout(rootView);
        rootView = initListView(rootView);
        rootView = initTextView(rootView);

        return rootView;
    }

    private View initSwipeRefreshLayout(View rootView) {
        swipeRefreshLayout =
                (SwipeRefreshLayout) rootView.findViewById(R.id.swipeRefreshLayoutWeatherForecast);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_light);
        return rootView;
    }

    private View initListView(View rootView) {
        listView = (ListView) rootView.findViewById(R.id.listViewWeatherForecast);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listenerItemPressed != null)
                    listenerItemPressed.itemPressed(adapter.getItem(position));
            }
        });
        adapter = new WeatherAdapter(context, dbManager.getWeatherForecasts());
        listView.setAdapter(adapter);
        return rootView;
    }

    private View initTextView(View rootView) {
        textViewCity = (TextView) rootView.findViewById(R.id.textViewWeatherForecastCity);
        textViewCity.setText(PreferenceHelper.getPreference(R.string.city));
        return rootView;
    }

    public void refresh() {
        if (NetworkHelper.isNetworkAvailable()) {
            dbManager.drop();
            dbManager.downloadWeather();
            adapter = new WeatherAdapter(context, dbManager.getWeatherForecasts());
            listView.setAdapter(adapter);
        } else  if (listenerRefresh != null) {
            listenerRefresh.showAlertDialog(getActivity(), "Интернет соединение отсутствует",
                    "При загрузке данных произошла ошибка.\nПроверьте Ваше подключение к сети.", false);
        }
    }

    @Override
    public void onRefresh() {
        refresh();
        swipeRefreshLayout.setRefreshing(false);
    }
}
