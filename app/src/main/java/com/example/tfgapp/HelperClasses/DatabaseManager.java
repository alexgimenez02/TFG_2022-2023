package com.example.tfgapp.HelperClasses;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseManager {
    private int limitTime = 24*60*60*1000; //24 hours
    private final HashMap<String, JSONObject> dbLoadedItems;
    public FirebaseDatabase databaseIntance;

    public DatabaseManager(String url){
        databaseIntance = FirebaseDatabase.getInstance(url != null ? url : "");
        dbLoadedItems = new HashMap<>();
    }
    public void writeToDatabase(@NonNull String path, @NonNull JSONObject content){
        DatabaseReference dbReference = databaseIntance.getReference(path);
        dbReference.setValue(content.toString()).addOnFailureListener(Throwable::printStackTrace);
    }

    public JSONObject readFromDatabase(@NonNull String path){
        DatabaseReference dbReference = databaseIntance.getReference(path);
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String readedValue = (String) dataSnapshot.getValue();
                String key = dataSnapshot.getKey();
                JSONObject obj;
                try {
                    obj = new JSONObject(readedValue);
                    DatabaseManager.this.dbLoadedItems.putIfAbsent(key, obj);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        return dbLoadedItems.get(path);
    }
    public AtomicBoolean databaseIsEmpty(){
        final AtomicBoolean isEmpty = new AtomicBoolean(true);
        DatabaseReference dbReference = databaseIntance.getReference();
        dbReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    isEmpty.set(false);
                    System.out.println(snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }

        });
        System.out.println(isEmpty.get() ? "True" : "False");
        return isEmpty;
    }
    public void deleteItemFromDatabase(@NonNull String path){
        DatabaseReference dbReference = databaseIntance.getReference();
        dbReference.child(path).getDatabase().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    dataSnapshot.getRef().removeValue();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled", error.toException());
            }
        });
    }
    /**
     * @param time: Time in ms
     *
     * */
    public void deleteAfterTimeHasPassed(int time){
        if(time >= limitTime) {
                DatabaseReference dbReference = databaseIntance.getReference();
                dbReference.setValue(null);
        }
    }


    public void changeLimitTime(int time){
        limitTime = time;
    }

}
