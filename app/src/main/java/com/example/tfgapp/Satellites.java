package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Satellites extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_satellites);

        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, MainActivity.class);
            startActivity(intent);
        });
        planetsButton.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, PlanetsSelector.class);
            startActivity(intent);
        });
        satellitesButton.setOnClickListener(view -> Toast.makeText(Satellites.this, "Already on this activity!", Toast.LENGTH_SHORT).show());
        othersButton.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, OthersActivity.class);
            startActivity(intent);
        });

        ImageView moonImage = findViewById(R.id.moon_clickable);
        ImageView jamesWebImage = findViewById(R.id.james_web_clickable);
        ImageView hubbleImage = findViewById(R.id.Hubble_clickable);


        moonImage.setClickable(true);
        moonImage.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, MoonActivity.class);
            startActivity(intent);
        });

        jamesWebImage.setClickable(true);
        jamesWebImage.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, JamesWebbActivity.class);
            startActivity(intent);
        });

        hubbleImage.setClickable(true);
        hubbleImage.setOnClickListener(view -> {
            Intent intent = new Intent(Satellites.this, HubbleActivity.class);
            startActivity(intent);
        });
    }
}