package com.example.tfgapp;

import static android.app.UiModeManager.MODE_NIGHT_NO;
import static android.app.UiModeManager.MODE_NIGHT_YES;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.tfgapp.HelperClasses.DatabaseManager;

public class ConfigurationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        SettingsFragment.c = ConfigurationActivity.this;
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private boolean isLightMode;
        private DatabaseManager dbManager;
        @SuppressLint("StaticFieldLeak")
        private static Context c;
        @SuppressLint("WrongConstant")
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
            isLightMode = false;
            SwitchPreference darkLightPreference = getPreferenceManager().findPreference("lightDark");
            SwitchPreference clearDatabaseCachePreference = getPreferenceManager().findPreference("cache");

            assert clearDatabaseCachePreference != null;
            clearDatabaseCachePreference.setOnPreferenceClickListener(preference -> {
                dbManager.clearDatabase();
                Toast.makeText(c, "Database caché cleared!", Toast.LENGTH_LONG).show();
                try{
                    Intent intent = new Intent(c, LoadActivity.class);
                    startActivity(intent);
                    return true;
                }catch(Exception ignored){
                    return false;
                }
            });

            assert darkLightPreference != null;
            darkLightPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                isLightMode = !isLightMode;
                int mode = isLightMode ? MODE_NIGHT_NO : MODE_NIGHT_YES;
                AppCompatDelegate.setDefaultNightMode(mode);
                return isLightMode;
            });
        }
    }
}
