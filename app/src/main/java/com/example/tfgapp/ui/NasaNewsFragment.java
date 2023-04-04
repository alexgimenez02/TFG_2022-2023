package com.example.tfgapp.ui;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
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


public class NasaNewsFragment extends Fragment {

    DatabaseManager dbManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nasa_news, container, false);
        dbManager = new DatabaseManager(getResources().getString(R.string.FirebaseURL));
        DatabaseReference dbRef = dbManager.databaseIntance.getReference("NEarth");
        final JSONObject[] newsFetched = new JSONObject[1];
                dbRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("ResourceAsColor")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String readedValue = (String) snapshot.getValue();
                try {
                    assert readedValue != null;
                    //Fetch the FIRST article
                    newsFetched[0] = new JSONObject(readedValue);
                    JSONArray articles = newsFetched[0].getJSONArray("articles");
                    JSONObject firstArticle = articles.getJSONObject(0);

                    TextView title = requireView().findViewById(R.id.NewsEarthTitle);
                    TextView body = requireView().findViewById(R.id.NewsBody);
                    TextView author = requireView().findViewById(R.id.NewsAuthor);
                    TextView viewDate = requireView().findViewById(R.id.NewsDate);
                    TextView url = requireView().findViewById(R.id.NewsURL);

                    title.setText(firstArticle.getString("title"));
                    String contentFromNews = firstArticle.getString("content");
                    String[] splittedContent = contentFromNews.split("…");
                    body.setText(String.format("%s... To finish reading this article, go to the URL below",splittedContent[0] ));
                    author.setText(String.format("By: %s",firstArticle.getString("author")));

                    String date = firstArticle.getString("publishedAt");
                    String[] splittedDate = date.split("T");
                    viewDate.setText(String.format("%s",splittedDate[0]));
                    url.setClickable(true);
                    url.setMovementMethod(LinkMovementMethod.getInstance());
                    url.setTextColor(R.color.teal_700);
                    String text = String.format("<a href='%s'> Read more </a>", firstArticle.getString("url"));
                    url.setText(Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT));

                    String imgUrl = firstArticle.getString("urlToImage");
                    ImageView imageView = requireView().findViewById(R.id.NewsEarthImage);
                    Glide.with(NasaNewsFragment.this).load(imgUrl).into(imageView);


                } catch (JSONException e) {
                    Toast.makeText(getContext(),"Error getting news loaded", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }
}

