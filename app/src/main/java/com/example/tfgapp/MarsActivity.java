package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.MarsNewsFragment;
import com.example.tfgapp.ui.MarsPhotoFragment;

public class MarsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mars);

        Fragment fragment = new MarsPhotoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout4,fragment)
                .commit();

        Fragment fragment2 = new MarsNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout3,fragment2)
                .commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}