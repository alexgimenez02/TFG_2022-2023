package com.example.tfgapp;

import android.os.Bundle;
import android.widget.TabHost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.ui.QueryAPOD;
import com.example.tfgapp.ui.QueryNEWS;
import com.example.tfgapp.ui.QueryROVER;

public class QueryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query);


        Fragment fragmentAPOD = new QueryAPOD();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.APOD,fragmentAPOD)
                .commit();
        Fragment fragmentRover = new QueryROVER();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.ROVER,fragmentRover)
                .commit();
        Fragment fragmentNews = new QueryNEWS();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.NEWS,fragmentNews)
                .commit();

        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec ts = tabHost.newTabSpec("APOD");
        ts.setContent(R.id.APOD);
        ts.setIndicator("APOD");
        tabHost.addTab(ts);

        TabHost.TabSpec ts2 = tabHost.newTabSpec("ROVER");
        ts2.setContent(R.id.ROVER);
        ts2.setIndicator("ROVER");
        tabHost.addTab(ts2);

        TabHost.TabSpec ts3 = tabHost.newTabSpec("NEWS");
        ts3.setContent(R.id.NEWS);
        ts3.setIndicator("NEWS");
        tabHost.addTab(ts3);
    }
}