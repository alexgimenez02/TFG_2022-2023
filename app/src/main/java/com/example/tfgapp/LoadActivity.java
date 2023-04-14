package com.example.tfgapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.HelperClasses.DatabaseManager;

import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoadActivity extends AppCompatActivity {

    protected DatabaseManager dbManager;
    private LinkedList<String> urls;
    private LinkedList<String> dbKeys;
    private LinkedList<Boolean> allItemsLoaded;
    private HashMap<String, JSONObject> dbObjects;
    int dbKeyIndex = 0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        String APOD_URL = String.format("https://api.nasa.gov/planetary/apod?api_key=%s", getResources().getString(R.string.NASA_API_KEY));
        String WitISS = "https://api.wheretheiss.at/v1/satellites/?id=25544";
        DateTimeFormatter format = DateTimeFormatter
                .ofPattern("yyyy-MM-dd");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime then = now.minusDays(7);
        LocalDateTime yesterday = now.minusDays(1);

        String NewsForEarth = String.format("https://newsapi.org/v2/everything?q=\"Earth\"&sources=engadget&from=%s&sortBy=relevancy&apiKey=%s", then.format(format), getResources().getString(R.string.NEWS_API_KEY));
        String NewsForMars = String.format("https://newsapi.org/v2/everything?q=\"Mars\"&sources=engadget&from=%s&sortBy=relevancy&apiKey=%s", then.format(format), getResources().getString(R.string.NEWS_API_KEY));

        String marsPhotos = String.format("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=%s&api_key=%s", yesterday.format(format), getResources().getString(R.string.NASA_API_KEY));

        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        urls = new LinkedList<>();
        dbKeys = new LinkedList<>();
        allItemsLoaded = new LinkedList<>();
        dbObjects = new HashMap<>();
        urls.add(WitISS);
        urls.add(NewsForEarth);
        urls.add(NewsForMars);
        urls.add(APOD_URL);
        urls.add(marsPhotos);
        dbKeys.add("WitISS");
        dbKeys.add("NEarth");
        dbKeys.add("NMars");
        dbKeys.add("APOD");
        dbKeys.add("MPhotos");
        //read all database
        ExecutorService executorService = Executors.newFixedThreadPool(urls.size());

        List<Future<JSONObject>> futures = new ArrayList<>();


        for (int i = 0; i < urls.size(); i++) {
            String url = urls.get(i);
            Future<JSONObject> future = executorService.submit(() -> {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response response = client.newCall(request).execute();
                String responseBody = response.body().string();
                return new JSONObject(responseBody);
            });
            futures.add(future);
        }

        // Wait for all the API calls to finish and retrieve their results
        List<JSONObject> results = new ArrayList<>();
        for (Future<JSONObject> future : futures) {
            JSONObject result = null;
            try {
                result = future.get();
            } catch (ExecutionException | InterruptedException e) {
                Toast.makeText(LoadActivity.this,"Failed at loading APIs",Toast.LENGTH_SHORT).show();
            }
            results.add(result);
        }

        executorService.shutdown();

        for(int i = 0; i < dbKeys.size(); i++){
            dbManager.writeToDatabase(dbKeys.get(i),results.get(i));
        }
        Toast.makeText(LoadActivity.this, "Loading all API calls!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(LoadActivity.this,MainActivity.class);
        startActivity(intent);
    }
}