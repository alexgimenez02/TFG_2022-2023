package com.example.tfgapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tfgapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryAPOD extends Fragment {
    EditText etPlannedDate;
    TextView titleView, dateView, descView;
    ImageView imageView;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query_apod, container, false);

        etPlannedDate = (EditText) view.findViewById(R.id.etPlannedDate);
        etPlannedDate.setOnClickListener(view1 -> {
            switch (view1.getId()) {
                case R.id.etPlannedDate:
                    showDatePickerDialog();
                    break;
            }
        });


        //Api call
        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(view -> {
            String fetchedDate = etPlannedDate.getText().toString();
            if(!"".equals(fetchedDate)) {
                titleView = requireView().findViewById(R.id.Title);
                dateView = requireView().findViewById(R.id.DateFromAPI);
                descView = requireView().findViewById(R.id.Description);
                imageView = requireView().findViewById(R.id.Image);
                String url = String.format("https://api.nasa.gov/planetary/apod?api_key=%s&date=%s", getResources().getString(R.string.NASA_API_KEY), fetchedDate);

                try {
                    JSONObject result = getApiCall(url).get();

                    titleView.setText((CharSequence) result.get("title"));
                    dateView.setText((CharSequence) result.get("date"));
                    descView.setText((CharSequence) result.get("explanation"));
                    Glide.with(QueryAPOD.this).load(result.get("hdurl")).into(imageView);

                    titleView.setVisibility(View.VISIBLE);
                    dateView.setVisibility(View.VISIBLE);
                    descView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                } catch (ExecutionException | InterruptedException | JSONException e) {
                    Toast.makeText(getContext(),"Error fetching API!",Toast.LENGTH_SHORT).show();
                }

            }
        });

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