package com.example.tfgapp.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link JamesWebbPhotoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JamesWebbPhotoFragment extends Fragment {

    DatabaseManager dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_james_webb_photo, container, false);
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        DatabaseReference dbRef = dbManager.databaseIntance.getReference("PJWST");
        final JSONObject[] newsFetched = new JSONObject[1];
        dbRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String readedValue = (String) snapshot.getValue();
                try {
                    assert readedValue != null;

                    TextView title = requireView().findViewById(R.id.titleImage);
                    TextView body = requireView().findViewById(R.id.NewsBody);
                    TextView viewDate = requireView().findViewById(R.id.NewsDate);
                    ImageView imageView = requireView().findViewById(R.id.JWSTimage);
                    //Fetch the FIRST article
                    newsFetched[0] = new JSONObject(readedValue);
                    JSONArray items = newsFetched[0].getJSONObject("collection").getJSONArray("items");
                    JSONObject firstArticle = items.getJSONObject(new Random().nextInt(items.length()));
                    JSONArray data = firstArticle.getJSONArray("data");
                    JSONArray links = firstArticle.getJSONArray("links");
                    JSONObject dataJSON = data.getJSONObject(0);
                    JSONObject linksJSON = links.getJSONObject(0);
                    title.setText(dataJSON.getString("title"));
                    body.setText(dataJSON.getString("description"));

                    String date = dataJSON.getString("date_created");
                    String[] splittedDate = date.split("T");
                    viewDate.setText(String.format("%s",splittedDate[0]));

                    String imgUrl = linksJSON.getString("href");
                    Glide.with(JamesWebbPhotoFragment.this).load(imgUrl).into(imageView);

                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Error getting images loaded", Toast.LENGTH_LONG).show();
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