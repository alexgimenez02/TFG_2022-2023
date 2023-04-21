package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.HubbleNewsFragment;
import com.example.tfgapp.ui.HubblePhotoFragment;

public class HubbleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hubble);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new HubbleNewsFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.HubbleNews,fragment)
                .commit();

        Fragment fragment1 = new HubblePhotoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.HubblePhotos,fragment1)
                .commit();
    }
}