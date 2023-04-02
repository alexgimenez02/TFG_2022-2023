package com.example.tfgapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.tfgapp.HelperClasses.DatabaseManager;

public class ConfigurationActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        Button dbCacheButton = findViewById(R.id.ClearDBButton);
        Button termsAndConds = findViewById(R.id.TermsAndConditions);
        Button greetings = findViewById(R.id.Greetings);

        dbCacheButton.setOnClickListener(view -> {
            dbManager.clearDatabase();
            Toast.makeText(ConfigurationActivity.this, "Database caché cleared!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ConfigurationActivity.this, LoadActivity.class);
            startActivity(intent);
        });
        termsAndConds.setOnClickListener(view -> {
            String url = "https://pastebin.com/nf1pbgDF";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });
        greetings.setOnClickListener(view -> {
            String url = "https://pastebin.com/W3tcHQ7G";
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            startActivity(intent);
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Añadir un GoBackButton
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {

        private boolean isLightMode;
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            SwitchPreference darkLightPreference = getPreferenceManager().findPreference("lightDark");
            isLightMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO;
            assert darkLightPreference != null;
            darkLightPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                isLightMode = !isLightMode;
                @AppCompatDelegate.NightMode int mode = isLightMode ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;
                AppCompatDelegate.setDefaultNightMode(mode);
                return isLightMode;
            });
        }
    }
}
