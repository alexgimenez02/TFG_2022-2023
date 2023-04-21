package com.example.tfgapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.JamesWebbFragment;
import com.example.tfgapp.ui.JamesWebbPhotoFragment;

public class JamesWebbActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_james_webb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Fragment fragment = new JamesWebbPhotoFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.JWSTphotos,fragment)
                .commit();

        Fragment fragment1 = new JamesWebbFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.newsJWST,fragment1)
                .commit();
    }
}