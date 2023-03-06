package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
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
    }
}