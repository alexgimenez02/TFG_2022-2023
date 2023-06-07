package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class GamesSelectorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_selector);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Button pButton = findViewById(R.id.playButton);
        pButton.setOnClickListener(view -> {
           String packageName = "com.DefaultCompany.com.unity.template.mobile2D";
            Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null) {
                Toast.makeText(this, "Launching game!", Toast.LENGTH_SHORT);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(this, "No Space Invaders Game installed", Toast.LENGTH_SHORT);
            }
        });
    }
}