package com.example.user.finish_project.activities;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.user.finish_project.FormatWeather;
import com.example.user.finish_project.NetworkHelper;
import com.example.user.finish_project.PreferenceHelper;
import com.example.user.finish_project.R;
import com.example.user.finish_project.ResourceHelper;
import com.example.user.finish_project.Weather;
import com.example.user.finish_project.fragments.DetailFragment;
import com.example.user.finish_project.fragments.SettingsFragment;
import com.example.user.finish_project.fragments.WeatherFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        WeatherFragment.OnItemPressed,
        WeatherFragment.OnRefresh {

    private Boolean exitNow;

    private SettingsFragment settingsFragment;
    private WeatherFragment weatherFragment;
    private DetailFragment detailWeatherFragment;

    private Boolean isSettingsFragmentActive;
    private Boolean isWeatherFragmentActive;

    /* ============================================ onCreate ======================================== */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        exitNow = false;

        toolbarAndNavigationDrawerInit();
        setContexts();
        initSharedPreferences();
        initFragments();
        setWeatherFragment();
    }

    private void toolbarAndNavigationDrawerInit() {
        // toolbar - ссылка на виджет
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // вызов setSupportActionBar() - чтобы Toolbar отобразился в методе onCreate()
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setContexts() {
        FormatWeather.setContext(this);
        PreferenceHelper.setContext(this);
        NetworkHelper.setContext(this);
        ResourceHelper.setContext(this);
    }

    private void initSharedPreferences() {
        if (PreferenceHelper.getPreference(R.string.metric) == null) {
            PreferenceHelper.setPreference(R.string.metric, getString(R.string.celsium));
        }
        if (PreferenceHelper.getPreference(R.string.city) == null) {
            if (getString(R.string.lang).equals("en")) {
                PreferenceHelper.setPreference(R.string.city, "Koeln");
            } else {
                PreferenceHelper.setPreference(R.string.city, "Koeln");
            }
        }
    }

    private void initFragments() {
        settingsFragment = new SettingsFragment();
        weatherFragment = new WeatherFragment();
        detailWeatherFragment = new DetailFragment();
    }


    /* ======================================= onBackPressed ======================================== */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            exitNow = false;
            drawer.closeDrawer(GravityCompat.START);
        } else {
            onFragmentBackPressed();
        }
    }

    private void onFragmentBackPressed() {
        if (!isWeatherFragmentActive) {
            if (isSettingsFragmentActive) {
                refresh();
            } else {
                setWeatherFragment();
            }
        } else {
            if (exitNow) {
                super.onBackPressed();
            } else {
                Toast.makeText(this, getString(R.string.back), Toast.LENGTH_SHORT).show();
                exitNow = true;
            }
        }
    }



/* =============================== onNavigationItemSelected ===================================== */

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        }

        switch (id) {
            case R.id.nav_restart:
                refresh();
                break;
            case R.id.nav_settings:
                setSettingsFragment();
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refresh() {
        setWeatherFragment();
        weatherFragment.refresh();
    }


    /* ======================================== setFragment ========================================= */

    // при запуске приложения MainActivity загружает фрагмент weatherFragment
    private void setWeatherFragment() {
        setCurrentFragment(weatherFragment);
        isWeatherFragmentActive = true;
        isSettingsFragmentActive = false;
    }

    private void setSettingsFragment() {
        setCurrentFragment(settingsFragment);
        isSettingsFragmentActive = true;
        isWeatherFragmentActive = false;
    }

    private void setFragment(Fragment fragment) {
        setCurrentFragment(fragment);
        isWeatherFragmentActive = false;
        isSettingsFragmentActive = false;
    }

    // подгрузка нужного фрагмента
    private void setCurrentFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commit();
        exitNow = false;
    }


    /* ====================================== Interfaces ============================================ */
    @Override
    protected void onPause() {
        super.onPause();
        weatherFragment.removeOnItemPressedListener();
        weatherFragment.removeOnRefreshListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        weatherFragment.setOnItemPressedListener(this);
        weatherFragment.setOnRefreshListener(this);
    }

    public void itemPressed(Weather weather) {
        detailWeatherFragment.getWeather(weather);
        setFragment(detailWeatherFragment);
    }

    // Интернет
    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();

        //Настраиваем название Alert Dialog:
        alertDialog.setTitle(title);

        //Настраиваем сообщение:
        alertDialog.setMessage(message);

        //Настраиваем кнопку OK
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        //Отображаем сообщение диалога:
        alertDialog.show();
    }
}