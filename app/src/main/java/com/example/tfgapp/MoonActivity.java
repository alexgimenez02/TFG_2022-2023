package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MoonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}