package com.example.tfgapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

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
    CoordinatorLayout layout;
    public String url= "https://api.nasa.gov/planetary/apod?api_key=DUYFvcd7IDPqXhozPfQkLJ8Dmz3A987BqBmQiZdr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = new CoordinatorLayout(getApplicationContext());

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
                            textView_desc.setTextSize(10.0f);
                            ImageView imageView = findViewById(R.id.imageView);
                            Glide.with(MainActivity.this).load(imgUrl).into(imageView);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    });
                }


            }
        });
    }
}