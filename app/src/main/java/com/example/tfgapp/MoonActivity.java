package com.example.tfgapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.HelperClasses.MoonPhase;
import com.example.tfgapp.ui.MoonNewsFragment;

import java.util.Calendar;

public class MoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView moon_phase_view = findViewById(R.id.moonPhase);

        MoonPhase mp = new MoonPhase(Calendar.getInstance());

        moon_phase_view.setText(mp.getPhaseIndexString(mp.getPhaseIndex()));



        Fragment fragment = new MoonNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.moonNewsFrame,fragment)
                .commit();
    }
}