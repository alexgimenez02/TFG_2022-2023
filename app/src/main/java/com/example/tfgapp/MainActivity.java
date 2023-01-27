package com.example.tfgapp;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    TextView txtString;
    String imgUrl = "", date = "", description = "", title = "";


    public String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        url = getResources().getString(R.string.apod_url);
        url = String.format("https://api.nasa.gov/planetary/apod?api_key=%s", getResources().getString(R.string.NASA_API_KEY));
        try {
            run();


        } catch (IOException e) {
            e.printStackTrace();
        }
        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, MainActivity.class);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "Already on this activity!", Toast.LENGTH_SHORT).show();
            }
        });
        planetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, PlanetsDrawerActivity.class);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "Going to planets selection!", Toast.LENGTH_SHORT).show();
            }
        });
        satellitesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SatellitesDrawerActivity.class);
//                startActivity(intent);
                Toast.makeText(MainActivity.this, "Going to satellites selection!", Toast.LENGTH_SHORT).show();
            }
        });
        othersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OthersActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "Going to others!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                if (response.body() != null) {
                    final String myResponse = response.body().string();
                    MainActivity.this.runOnUiThread(() -> {
                        try {

                            JSONObject obj = new JSONObject(myResponse);
                            imgUrl = obj.getString("url");
                            date = obj.getString("date");
                            description = obj.getString("explanation");
                            title = obj.getString("title");
                            TextView textView_title = findViewById(R.id.textView1);
                            TextView textView_date = findViewById(R.id.textView2);
                            TextView textView_desc = findViewById(R.id.textView3);
                            textView_title.setText(String.format("Title: %s", title));
                            textView_title.setTextSize(12.0f);
                            textView_date.setText(String.format("Date:%s", date));
                            textView_date.setTextSize(8.0f);
                            textView_desc.setText(String.format("Description: %s", description));
                            textView_desc.setTextSize(7.0f);
                            ImageView imageView = findViewById(R.id.imageView);
                            ImageView largeImageView = findViewById(R.id.largeImageView);
                            Glide.with(MainActivity.this).load(imgUrl).into(imageView);
                            Glide.with(MainActivity.this).load(imgUrl).into(largeImageView);
                            imageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ViewGroup rootView=findViewById(R.id.MainLayout);

                                    for(int i=0;i<rootView.getChildCount();i++){

                                        View currView=rootView.getChildAt(i);



                                            //Do something
                                            currView.setVisibility(View.INVISIBLE);


                                    }

                                    largeImageView.setForegroundGravity(100);
                                    largeImageView.setVisibility(View.VISIBLE);


                                }
                            });
                            largeImageView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    ViewGroup rootView=findViewById(R.id.MainLayout);

                                    for(int i=0;i<rootView.getChildCount();i++){

                                        View currView=rootView.getChildAt(i);
                                        //Do something
                                        currView.setVisibility(View.VISIBLE);
                                    }
                                    largeImageView.setForegroundGravity(100);
                                    largeImageView.setVisibility(View.INVISIBLE);
                                }

                            });

                            TextView dwld =findViewById(R.id.Download);
                            TextView background =findViewById(R.id.SABG);
                            dwld.setClickable(true);
                            background.setClickable(true);
                            dwld.setMovementMethod(LinkMovementMethod.getInstance());
                            background.setMovementMethod(LinkMovementMethod.getInstance());
                            String text = String.format("<a href='%s'> Download </a>", imgUrl);
                            dwld.setText(Html.fromHtml(text,Html.FROM_HTML_MODE_COMPACT));
                            background.setClickable(true);
                            background.setOnClickListener(new View.OnClickListener(){

                                @SuppressLint("ResourceType")
                                @Override
                                public void onClick(View view) {
                                    imageView.buildDrawingCache(true);
                                    Bitmap bmp = imageView.getDrawingCache(true);
                                    WallpaperManager m=WallpaperManager.getInstance(MainActivity.this);

                                    try {
                                        m.setBitmap(bmp);
                                        Toast.makeText(MainActivity.this, "Wallpaper Set Successfully!!", Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        Toast.makeText(MainActivity.this, "Setting WallPaper Failed!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }


            }
        });
    }
}