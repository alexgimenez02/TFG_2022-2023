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
        Button configButton = findViewById(R.id.Config);
        Button gamesButton = findViewById(R.id.Games);
        Button queryButton = findViewById(R.id.Queries);
        Button sendFeedbackButton = findViewById(R.id.BugReport);

        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(OthersActivity.this, MainActivity.class);
            startActivity(intent);
        });
        planetsButton.setOnClickListener(view -> {
            Intent intent = new Intent(OthersActivity.this, PlanetsSelector.class);
            startActivity(intent);
        });
        satellitesButton.setOnClickListener(view -> {
            Intent intent = new Intent(OthersActivity.this, Satellites.class);
            startActivity(intent);
        });
        othersButton.setOnClickListener(view -> Toast.makeText(OthersActivity.this, "Already in this activity!", Toast.LENGTH_SHORT).show());

        configButton.setOnClickListener(view -> startActivity(new Intent(OthersActivity.this, ConfigurationActivity.class)));
        gamesButton.setOnClickListener(view -> startActivity(new Intent(OthersActivity.this, GamesSelectorActivity.class)));
        queryButton.setOnClickListener(view -> startActivity(new Intent(OthersActivity.this, QueryActivity.class)));
        sendFeedbackButton.setOnClickListener(view -> startActivity(new Intent(OthersActivity.this, ReportBugActivity.class)));


    }
}