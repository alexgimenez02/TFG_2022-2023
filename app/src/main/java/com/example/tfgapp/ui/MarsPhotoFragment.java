package com.example.tfgapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tfgapp.HelperClasses.DatabaseManager;
import com.example.tfgapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class MarsPhotoFragment extends Fragment {

    DatabaseManager dbManager;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_mars_photo, container, false);

       dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
       DatabaseReference dbRef = dbManager.databaseIntance.getReference("MPhotos");
       final JSONObject[] photoFetched = new JSONObject[1];
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String readedValue = (String) snapshot.getValue();
                try {
                    assert readedValue != null;
                    //Fetch the FIRST article
                    photoFetched[0] = new JSONObject(readedValue);
                    JSONArray photos = photoFetched[0].getJSONArray("photos");
                    ImageView imageView = requireView().findViewById(R.id.MarsPhoto);
                    if(photos.length() == 0)
                    {
                        imageView.setVisibility(View.INVISIBLE);
                        TextView placeholder = requireView().findViewById(R.id.PlaceholderText);
                        placeholder.setVisibility(View.VISIBLE);

                    }else {
                        JSONObject firstQuery = photos.getJSONObject(new Random().nextInt(photos.length()));

                        String imgUrl = firstQuery.getString("img_src");
                        Glide.with(MarsPhotoFragment.this).load(imgUrl).into(imageView);
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Error getting photos loaded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

       // Inflate the layout for this fragment
        return view;
    }
}