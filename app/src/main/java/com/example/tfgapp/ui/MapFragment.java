package com.example.tfgapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.tfgapp.HelperClasses.DatabaseManager;
import com.example.tfgapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class MapFragment extends Fragment {

    DatabaseManager dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));


        View view = inflater.inflate(R.layout.fragment_map,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.MY_MAP);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(googleMap -> {
            MarkerOptions markerOptions = new MarkerOptions();
            DatabaseReference dbRef = dbManager.databaseIntance.getReference("WitISS");
            final JSONObject[] posISS = new JSONObject[1];
            dbRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String readedValue = (String) snapshot.getValue();
                    try {
                        assert readedValue != null;
                        posISS[0] = new JSONObject(readedValue);
                        LatLng latLng1 = new LatLng(posISS[0].getDouble("latitude"),posISS[0].getDouble("longitude"));
                        markerOptions.position(latLng1);
                        markerOptions.title(latLng1.latitude + " KG " + latLng1.longitude);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng1, 8));
                        googleMap.addMarker(markerOptions);
                    } catch (JSONException e) {
                        Toast.makeText(getContext(),"Error loading ISS position", Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return view;
    }
}