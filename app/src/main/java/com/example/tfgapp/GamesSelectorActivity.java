package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.databinding.ActivityGamesSelectorBinding;

public class GamesSelectorActivity extends AppCompatActivity {

    private ActivityGamesSelectorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_games_selector);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}