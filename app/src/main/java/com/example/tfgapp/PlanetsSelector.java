package com.example.tfgapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class PlanetsSelector extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
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



        ImageView solarSystem = findViewById(R.id.solar_system);

        solarSystem.setOnTouchListener((view, motionEvent) -> {
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();

            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                final int mercury = solarSystem.getWidth() / 8;
                final int venus = solarSystem.getWidth() * 2 / 8;
                final int earth = solarSystem.getWidth() * 3 / 8;
                final int mars = solarSystem.getWidth() * 4 / 8;
                final int jupiter = solarSystem.getWidth() * 5 / 8;
                final int saturn = solarSystem.getWidth() * 6 / 8;
                final int uranus = solarSystem.getWidth() * 7 / 8;
                final int neptune = solarSystem.getWidth();
                if(x <= mercury && x > 0){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }
                else if (x <= venus && x > mercury){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }
                else if (x <= earth && x > venus){
                    Intent intent = new Intent(PlanetsSelector.this, EarthActivity.class);
                    startActivity(intent);
                }
                else if (x <= mars && x > earth){
                    Intent intent = new Intent(PlanetsSelector.this, MarsActivity.class);
                    startActivity(intent);
                }
                else if (x <= jupiter && x > mars){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }
                else if (x <= saturn && x > jupiter){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }
                else if (x <= uranus && x > saturn){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }
                else if (x <= neptune && x > uranus){
                    Toast.makeText(PlanetsSelector.this,"This is not available!", Toast.LENGTH_SHORT).show();
                }

                return true;
            }
            return false;
        });


    }
}