package com.example.tfgapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.HelperClasses.DatabaseManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class LoadActivity extends AppCompatActivity {

    protected DatabaseManager dbManager;
    private static List<String> urls;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        String url = String.format("https://api.nasa.gov/planetary/apod?api_key=%s", getResources().getString(R.string.NASA_API_KEY));
        urls.add(url);
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        JSONObject test = new JSONObject();
        try {
            test.put("Hello", "World");
            dbManager.writeToDatabase("test",test);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        new Handler().postDelayed(this::run, 3000);


    }


    void run() {

        OkHttpClient client = new OkHttpClient();
        Request request = null;
        for (String url: urls) {
            try{
                request = new Request.Builder()
                        .url(url)
                        .build();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(request == null){
                Toast.makeText(LoadActivity.this, "Loading all API calls!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoadActivity.this,MainActivity.class);
                startActivity(intent);
            }else{
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {

                        if (response.body() != null) {
                            LoadActivity.this.runOnUiThread(() -> {
                            });
                        }
                    }
                });
            }
        }

    }
}