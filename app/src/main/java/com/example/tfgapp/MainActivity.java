package com.example.tfgapp;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    TextView txtString;
    ImageView imageString;
    CoordinatorLayout layout;
    public String url= "https://api.nasa.gov/planetary/apod?api_key=DUYFvcd7IDPqXhozPfQkLJ8Dmz3A987BqBmQiZdr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = new CoordinatorLayout(getApplicationContext());
//        txtString= (TextView)findViewById(R.id.image_title);
        imageString= (ImageView)findViewById(R.id.imageView);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String imgUrl = "", date = "", description = "", title = "";
                            try {

                                JSONObject obj = new JSONObject(myResponse);
                                imgUrl = obj.getString("url");
                                date = obj.getString("date");
                                description = obj.getString("explanation");
                                title = obj.getString("title");


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

//                            txtString.setText(title);

                            imageString.setImageDrawable(LoadImageFromWebOperations(imgUrl));
                        }
                    });
                }

            }
        });
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream URLcontent = (InputStream) new URL(url).getContent();
            return Drawable.createFromStream(URLcontent, "");
        } catch (Exception e) {
            return null;
        }
    }
    //    private ActivityMainBinding binding;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        setContentView(binding.getRoot());
//
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
//        ViewPager viewPager = binding.viewPager;
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = binding.tabs;
//        tabs.setupWithViewPager(viewPager);
//        FloatingActionButton fab = binding.fab;
//
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//    }
}