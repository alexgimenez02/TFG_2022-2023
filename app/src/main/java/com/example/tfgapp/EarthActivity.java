package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.MapFragment;
import com.example.tfgapp.ui.NasaNewsFragment;
import com.google.android.gms.maps.MapView;

public class EarthActivity extends AppCompatActivity {

    private MapView googleMaps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earth);

        Fragment fragment = new MapFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout,fragment)
                .commit();
        Fragment fragment2 = new NasaNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout2,fragment2)
                .commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Añadir un GoBackButton

    }
}