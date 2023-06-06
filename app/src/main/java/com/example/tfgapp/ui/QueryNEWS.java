package com.example.tfgapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
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


public class QueryNEWS extends Fragment {

    EditText etPlannedDate, keyword;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_query_news, container, false);

        etPlannedDate = view.findViewById(R.id.etPlannedDate);
        keyword = view.findViewById(R.id.KeyWordInput);
        etPlannedDate.setOnClickListener(view1 -> {
            switch (view1.getId()) {
                case R.id.etPlannedDate:
                    showDatePickerDialog();
                    break;
            }
        });

        Button button = view.findViewById(R.id.button);
        button.setOnClickListener(view -> {
            String fetchedDate = etPlannedDate.getText().toString();
            if(!"".equals(fetchedDate) && !"".equals(keyword.getText().toString())) {
                TextView title = requireView().findViewById(R.id.title2);
                TextView link = requireView().findViewById(R.id.link);
                ImageView imageView = requireView().findViewById(R.id.image3);
                String url = String.format("https://newsapi.org/v2/everything?q=\"%s\"&from=%s&sortBy=relevancy&language=en&apiKey=%s",keyword.getText().toString(), fetchedDate, getResources().getString(R.string.NEWS_API_KEY));

                try {
                    JSONObject result = getApiCall(url).get();
                    JSONArray articles = result.getJSONArray("articles");
                    if(articles.length() == 0)
                    {
                        Toast.makeText(getContext(),String.format("No news about %s found!",keyword.getText().toString()),Toast.LENGTH_SHORT).show();
                        return;
                    }
                    title.setVisibility(View.VISIBLE);
                    link.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    JSONObject firstArticle = articles.getJSONObject(new Random().nextInt(articles.length()));
                    title.setText((CharSequence) firstArticle.get("title"));
                    link.setClickable(true);

                    String linkToNews = String.format("<a href='%s'> Link to news </a>",firstArticle.get("url"));
                    String currentUrl = (String) firstArticle.get("url");
                    link.setText(Html.fromHtml(linkToNews, Html.FROM_HTML_MODE_COMPACT));

                    link.setOnClickListener(view12 -> {
                        Uri uriUrl = Uri.parse(currentUrl);
                        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
                        startActivity(launchBrowser);
                    });

                    Glide.with(QueryNEWS.this).load(firstArticle.get("urlToImage")).into(imageView);



                } catch (ExecutionException | InterruptedException | JSONException e) {
                    Toast.makeText(getContext(),"Error fetching API!",Toast.LENGTH_SHORT).show();
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