package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.MoonNewsFragment;

public class MoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new MoonNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.moonNewsFrame,fragment)
                .commit();
    }
}