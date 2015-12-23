package com.example.user.finish_project.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.user.finish_project.PreferenceHelper;
import com.example.user.finish_project.R;
import com.example.user.finish_project.ResourceHelper;

public class SettingsFragment extends Fragment {
    EditText editTextCity;
    Button buttonSaveCity;
    Spinner spinnerMetric;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.settings_fragment, container, false);
        rootView = viewInit(rootView);
        return rootView;
    }

    private View viewInit(View rootView) {
        rootView = editTextCityInit(rootView);
        rootView = buttonSaveCityInit(rootView);
        rootView = spinnerMetricInit(rootView);
        return rootView;
    }

    private View editTextCityInit(View rootView) {
        editTextCity = (EditText) rootView.findViewById(R.id.editTextSettingsCity);
        editTextCity.setText(PreferenceHelper.getPreference(R.string.city));
        return rootView;
    }
    private View buttonSaveCityInit(View rootView) {
        buttonSaveCity = (Button) rootView.findViewById(R.id.buttonSettingsSaveCity);
        buttonSaveCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = editTextCity.getText().toString();
                PreferenceHelper.setPreference(R.string.city, city);
            }
        });
        return rootView;
    }
    private View spinnerMetricInit(View rootView) {
        spinnerMetric = (Spinner) rootView.findViewById(R.id.spinnerSettingsMetric);
        final String[] metrics = ResourceHelper.getStringArray(R.array.metrics);
        final ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, metrics);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMetric.setAdapter(adapter);
        spinnerMetric.setPrompt(ResourceHelper.getString(R.string.metric_title));
        if (PreferenceHelper.getPreference(R.string.metric).equals(ResourceHelper.getString(R.string.celsium))) {
            spinnerMetric.setSelection(0);
        } else {
            spinnerMetric.setSelection(1);
        }
        spinnerMetric.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String currentMetric = adapter.getItem(position);
                if (currentMetric.equals(metrics[0])) {
                    PreferenceHelper.setPreference(R.string.metric, ResourceHelper.getString(R.string.celsium));
                } else {
                    PreferenceHelper.setPreference(R.string.metric, ResourceHelper.getString(R.string.farenheit));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }
}
