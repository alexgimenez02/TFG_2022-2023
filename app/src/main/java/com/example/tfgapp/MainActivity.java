package com.example.tfgapp;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tfgapp.HelperClasses.DatabaseManager;

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
    String imgUrl = "";
    String date = "";
    String description = "";
    String title = "";
    DatabaseManager dbManager;

    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        run();


        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Already on this activity!", Toast.LENGTH_SHORT).show());
        planetsButton.setOnClickListener(view -> {
//                Intent intent = new Intent(MainActivity.this, PlanetsDrawerActivity.class);
//                startActivity(intent);
            Toast.makeText(MainActivity.this, "Going to planets selection!", Toast.LENGTH_SHORT).show();
        });
        satellitesButton.setOnClickListener(view -> {
//                Intent intent = new Intent(MainActivity.this, SatellitesDrawerActivity.class);
//                startActivity(intent);
            Toast.makeText(MainActivity.this, "Going to satellites selection!", Toast.LENGTH_SHORT).show();
        });
        othersButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OthersActivity.class);
            startActivity(intent);
            Toast.makeText(MainActivity.this, "Going to others!", Toast.LENGTH_SHORT).show();
        });
    }


    void run(){

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
                            TextView textViewTitle = findViewById(R.id.textView1);
                            TextView textViewDate = findViewById(R.id.textView2);
                            TextView textViewDesc = findViewById(R.id.textView3);
                            textViewTitle.setText(String.format("Title: %s", title));
                            textViewTitle.setTextSize(12.0f);
                            textViewDate.setText(String.format("Date:%s", date));
                            textViewDate.setTextSize(8.0f);
                            textViewDesc.setText(String.format("Description: %s", description));
                            textViewDesc.setTextSize(7.0f);
                            ImageView imageView = findViewById(R.id.imageView);
                            ImageView largeImageView = findViewById(R.id.largeImageView);
                            Glide.with(MainActivity.this).load(imgUrl).into(imageView);
                            Glide.with(MainActivity.this).load(imgUrl).into(largeImageView);
                            imageView.setOnClickListener(view -> {
                                ViewGroup rootView=findViewById(R.id.MainLayout);

                                for(int i=0;i<rootView.getChildCount();i++){

                                    View currView=rootView.getChildAt(i);



                                        //Do something
                                        currView.setVisibility(View.INVISIBLE);


                                }

                                largeImageView.setForegroundGravity(100);
                                largeImageView.setVisibility(View.VISIBLE);


                            });
                            largeImageView.setOnClickListener(view -> {
                                ViewGroup rootView=findViewById(R.id.MainLayout);

                                for(int i=0;i<rootView.getChildCount();i++){

                                    View currView=rootView.getChildAt(i);
                                    //Do something
                                    currView.setVisibility(View.VISIBLE);
                                }
                                largeImageView.setForegroundGravity(100);
                                largeImageView.setVisibility(View.INVISIBLE);
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