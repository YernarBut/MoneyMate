package com.example.android.moneymate2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.telecom.Conference;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends BaseActivity {
    private static final String PREFERENCES = "SWITCH_PREFERENCES";
    private static final String PREFERENCES2 = "MODE";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    private Spinner spinnerCurrency;
    private Switch switchCurrency;
    private Switch themeSwitch;
    private ImageButton backButton;
    private boolean nightMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        initViews();

        settings = getSharedPreferences(PREFERENCES2, Context.MODE_PRIVATE);
        nightMode = settings.getBoolean("nightMode", false);
        themeSwitch.setChecked(nightMode);

        themeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor = settings.edit();
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("nightMode", true);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("nightMode", false);
                }
                editor.apply();
            }
        });

        settings = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        boolean switchState = settings.getBoolean("switch_state", false);
        switchCurrency.setChecked(switchState);

        switchCurrency.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor = settings.edit();
                editor.putBoolean("switch_state", switchCurrency.isChecked());
                editor.apply();
            }
        });

        List<String> list = new ArrayList<String>() {{
            add("Tenge");
            add("Euro");
            add("Ruble");
            add("Pound Sterling");
            add("Yen");
            add("US Dollar");
        }};

        CustomAdapter adapter = new CustomAdapter(SettingsActivity.this,
                R.layout.item_spinner_title,
                R.layout.item_spinner_dropdown,
                list);
        spinnerCurrency.setAdapter(adapter);
        settings = getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        int positionSpinner = settings.getInt("positionSpinner", 0);
        spinnerCurrency.setSelection(positionSpinner);
        spinnerCurrency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                editor.putInt("positionSpinner", i);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.newIntent(SettingsActivity.this);
                startActivity(intent);
            }
        });
    }
    private void initViews() {
        backButton = findViewById(R.id.backButton);
        spinnerCurrency = findViewById(R.id.spinnerCurrency);
        switchCurrency = findViewById(R.id.switchCurrency);
        themeSwitch = findViewById(R.id.themeSwitch);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }
}