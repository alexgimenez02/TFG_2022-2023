package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OthersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_others);
        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(OthersActivity.this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(OthersActivity.this, "Going to Home page!", Toast.LENGTH_SHORT).show();
        });
        planetsButton.setOnClickListener(view -> {
//                Intent intent = new Intent(MainActivity.this, PlanetsDrawerActivity.class);
//                startActivity(intent);
            Toast.makeText(OthersActivity.this, "Going to planets selection!", Toast.LENGTH_SHORT).show();
        });
        satellitesButton.setOnClickListener(view -> {
//                Intent intent = new Intent(MainActivity.this, SatellitesDrawerActivity.class);
//                startActivity(intent);
            Toast.makeText(OthersActivity.this, "Going to satellites selection!", Toast.LENGTH_SHORT).show();
        });
        othersButton.setOnClickListener(view -> Toast.makeText(OthersActivity.this, "Already in this activity!", Toast.LENGTH_SHORT).show());
    }
}