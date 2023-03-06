package com.example.tfgapp;

import android.annotation.SuppressLint;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.tfgapp.HelperClasses.DatabaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;


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
//        Bundle extras = getIntent().getExtras();
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        run();


        Button homeButton = findViewById(R.id.Home);
        Button planetsButton = findViewById(R.id.planets);
        Button satellitesButton = findViewById(R.id.satellites);
        Button othersButton = findViewById(R.id.others);

        homeButton.setOnClickListener(view -> Toast.makeText(MainActivity.this, "Already on this activity!", Toast.LENGTH_SHORT).show());
        planetsButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, PlanetsSelector.class);
                startActivity(intent);
        });
        satellitesButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, Satellites.class);
                startActivity(intent);
        });
        othersButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, OthersActivity.class);
            startActivity(intent);
        });
    }


    void run(){
        if (dbManager.databaseIsEmpty().get()) {
            MainActivity.this.runOnUiThread(() -> {
                try {
                    DatabaseReference dbRef = dbManager.databaseIntance.getReference("APOD");
                    final JSONObject[] obj = new JSONObject[1];
                    dbRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String readedValue = (String) dataSnapshot.getValue();
                            try {
                                assert readedValue != null;
                                obj[0] = new JSONObject(readedValue);
                                imgUrl = obj[0].getString("url");
                                date = obj[0].getString("date");
                                description = obj[0].getString("explanation");
                                title = obj[0].getString("title");
                                TextView textViewTitle = findViewById(R.id.textView1);
                                TextView textViewDate = findViewById(R.id.textView2);
                                TextView textViewDesc = findViewById(R.id.textView3);
                                textViewTitle.setText(String.format("%s", title));
                                textViewTitle.setTextSize(12.0f);
                                textViewDate.setText(String.format("%s", date));
                                textViewDate.setTextSize(8.0f);
                                textViewDesc.setText(String.format("%s", description));
                                textViewDesc.setTextSize(7.0f);
                                ImageView imageView = findViewById(R.id.imageView);
                                Glide.with(MainActivity.this).load(imgUrl).into(imageView);
                                TextView dwld =findViewById(R.id.Download);
                                TextView background =findViewById(R.id.SABG);
                                dwld.setClickable(true);
                                background.setClickable(true);
                                dwld.setMovementMethod(LinkMovementMethod.getInstance());
                                background.setMovementMethod(LinkMovementMethod.getInstance());
                                String text = String.format("<a href='%s'> Download </a>", imgUrl);
                                dwld.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));
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
                            } catch (JSONException | AssertionError e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}