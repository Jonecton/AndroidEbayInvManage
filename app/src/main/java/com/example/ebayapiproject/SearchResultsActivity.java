package com.example.ebayapiproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity {
    private String searchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        searchString = getIntent().getStringExtra("searchString");
        new SearchEbayTask().execute(searchString);
    }

    private class SearchEbayTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String searchQuery = strings[0];
            String apiUrl = "https://api.ebay.com/...";

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(apiUrl);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
                reader = new BufferedReader(inputStreamReader);

                StringBuilder builder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }

                return builder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                parseSearchResults(result);
            } else {

            }
        }
    }

    private void parseSearchResults(String json) {
        Gson gson = new Gson();
        SearchResponse searchResponse = gson.fromJson(json, SearchResponse.class);

        if (searchResponse != null && searchResponse.getItems() != null) {
            List<SearchResponse.Item> items = searchResponse.getItems();

        } else {

        }
    }
}
