package com.example.tfgapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);
        new Handler().postDelayed(() -> {
            try {
                run();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 3000);

    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = null;
        try{
            request = new Request.Builder()
                    .url("https://")
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
                public void onFailure(Call call, IOException e) {
                    call.cancel();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    if (response.body() != null) {
                        LoadActivity.this.runOnUiThread(() -> {
                        });
                    }


                }
            });
        }
    }
}