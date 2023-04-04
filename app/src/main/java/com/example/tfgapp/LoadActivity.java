package com.example.tfgapp;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfgapp.HelperClasses.DatabaseManager;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedList;

import okhttp3.Call;
import okhttp3.Callback;
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

        String NewsForEarth = String.format("https://newsapi.org/v2/everything?q=\"Earth\"&sources=engadget&from=%s&sortBy=relevancy&apiKey=%s",then.format(format),getResources().getString(R.string.NEWS_API_KEY));

        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        urls = new LinkedList<>();
        dbKeys = new LinkedList<>();
        allItemsLoaded = new LinkedList<>();
        dbObjects = new HashMap<>();
        urls.add(WitISS);
        urls.add(APOD_URL);
        urls.add(NewsForEarth);
        dbKeys.add("WitISS");
        dbKeys.add("APOD");
        dbKeys.add("NEarth");
        urls.add("https://");
        //read all database
        for(String key: dbKeys){
            DatabaseReference dbRef = dbManager.databaseIntance.getReference(key);
            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    String readedValue = (String) dataSnapshot.getValue();
                    try {

                            assert readedValue != null;
                            dbObjects.put(key,new JSONObject(readedValue)) ;

                    } catch (JSONException | AssertionError e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Failed to read value
                    Log.w(TAG, "Failed to read value.", error.toException());
                }
            });
        }
        dbKeys.add("final");
        dbObjects.put("final",null);
        //Run all api calls
        new Handler(Looper.getMainLooper()).postDelayed(this::run, 3000);


    }


    void run() {

        OkHttpClient client = new OkHttpClient();
        Request request = null;

        for (String url: urls) {

            if(dbObjects.get(dbKeys.get(dbKeyIndex)) == null){
                try{
                    request = new Request.Builder()
                            .url(url)
                            .build();
                }catch (Exception e){
                    e.printStackTrace();
                    request = null;
                }
            }else {
                dbKeyIndex++;
                continue;
            }


            if(request == null){
                boolean startMainActivity = true;
                for(Boolean check: LoadActivity.this.allItemsLoaded){
                    if (Boolean.FALSE.equals(check)) {
                        startMainActivity = false;
                        break;
                    }
                }
                if(startMainActivity){
                    Toast.makeText(LoadActivity.this, "Loading all API calls!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoadActivity.this,MainActivity.class);
                    startActivity(intent);
                    break;
                }else{
                    Toast.makeText(LoadActivity.this, "API calls failed to load!", Toast.LENGTH_SHORT).show();
                }
            }else{
                client.newCall(request).enqueue(new Callback() {

                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        final String myResponse = response.body().string();
                        LoadActivity.this.runOnUiThread(() -> {
                            try {
                                JSONObject obj = new JSONObject(myResponse);
                                dbManager.writeToDatabase(LoadActivity.this.dbKeys.get(LoadActivity.this.dbKeyIndex),obj);
                                LoadActivity.this.allItemsLoaded.add(LoadActivity.this.dbKeyIndex, true);
                                LoadActivity.this.dbKeyIndex++;

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });

                    }
                });
            }
        }

    }
}