package com.example.tfgapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tfgapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class QueryROVER extends Fragment {

    EditText etPlannedDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_query_rover, container, false);

        etPlannedDate = (EditText) view.findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(view1 -> {
            switch (view1.getId()) {
                case R.id.etPlannedDate:
                    showDatePickerDialog();
                    break;
            }
        });
        Button button = view.findViewById(R.id.button4);
        button.setOnClickListener(view12 -> {
            String fetchedDate = etPlannedDate.getText().toString();
            String url = String.format("https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?earth_date=%s&api_key=%s", etPlannedDate.getText().toString(), getResources().getString(R.string.NASA_API_KEY));
            if(!"".equals(fetchedDate)) {
                TextView fullName = requireView().findViewById(R.id.Full_name);
                ImageView imageView = requireView().findViewById(R.id.image2);

                try {
                    JSONObject result = getApiCall(url).get();
                    JSONArray photos = result.getJSONArray("photos");
                    JSONObject randPhoto = photos.getJSONObject(new Random().nextInt(photos.length()));

                    fullName.setText((CharSequence) randPhoto.getJSONObject("camera").get("full_name"));
                    Glide.with(QueryROVER.this).load(randPhoto.get("img_src")).into(imageView);

                    fullName.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);


                } catch (ExecutionException |InterruptedException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        // Inflate the layout for this fragment
        return view;
    }
    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance((datePicker, year, month, day) -> {
            // +1 because January is zero
            final String selectedDate = year + "-" + (month+1) + "-" + day;
            etPlannedDate.setText(selectedDate);
        });

        newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");
    }
    private Future<JSONObject> getApiCall(String url)
    {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        return executorService.submit(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .build();
            Response response = client.newCall(request).execute();
            String responseBody = response.body().string();
            return new JSONObject(responseBody);
        });
    }
}

/*
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
 */