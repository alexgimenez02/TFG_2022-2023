package com.example.tfgapp.HelperClasses;

import static android.content.ContentValues.TAG;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DatabaseManager {
    private int limitTime = 24*60*60*1000; //24 hours
    FirebaseDatabase databaseIntance;

    public DatabaseManager(String url){
        databaseIntance = FirebaseDatabase.getInstance(url != null ? url : "");
    }
    public void writeToDatabase(@NonNull String path, @NonNull JSONObject content){
        DatabaseReference dbReference = databaseIntance.getReference(path);
        dbReference.setValue(content.toString()).addOnFailureListener(Throwable::printStackTrace);
    }

    public void readFromDatabase(@NonNull String path){
        final AtomicReference<JSONObject> readValue = new AtomicReference<>();
        DatabaseReference dbReference = databaseIntance.getReference(path);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                readValue.set(dataSnapshot.getValue(JSONObject.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
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
