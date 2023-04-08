package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class HubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}