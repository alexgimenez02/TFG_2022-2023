package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlanetsSelector extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planets_selector);

        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetsSelector.this, MainActivity.class);
            startActivity(intent);
        });
        planetsButton.setOnClickListener(view -> Toast.makeText(PlanetsSelector.this, "Already on this activity!", Toast.LENGTH_SHORT).show());
        satellitesButton.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetsSelector.this, Satellites.class);
            startActivity(intent);
        });
        othersButton.setOnClickListener(view -> {
            Intent intent = new Intent(PlanetsSelector.this, OthersActivity.class);
            startActivity(intent);
        });

        ImageView earth = findViewById(R.id.earth);
        ImageView mars = findViewById(R.id.mars);

        earth.setImageResource(R.drawable.earth_image);
        mars.setImageResource(R.drawable.mars_image);

        earth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanetsSelector.this, EarthActivity.class);
                startActivity(intent);
            }
        });
        mars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PlanetsSelector.this, MarsActivity.class);
                startActivity(intent);
            }
        });

    }
}