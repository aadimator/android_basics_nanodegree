package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * Base URL
     */
    public static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setEmptyView(findViewById(R.id.emptyListView));

        Button searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if Internet is Available
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

                if (isConnected) {
                    Toast.makeText(MainActivity.this, R.string.connectionSuccessMsg, Toast.LENGTH_LONG).show();
                    // Kick off an {@link AsyncTask} to perform the network request
                    BooksAsyncTask task = new BooksAsyncTask();
                    task.execute();
                } else {
                    Toast.makeText(MainActivity.this, R.string.connectionFailureMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    /**
     * Update the screen to display information from the given {@link ArrayList<Book>}.
     */
    private void updateUi(ArrayList<Book> books) {
        BookAdapter bookAdapter = new BookAdapter(this, books);
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(bookAdapter);
    }

    private class BooksAsyncTask extends AsyncTask<URL, Void, ArrayList<Book>> {
        @Override
        protected ArrayList<Book> doInBackground(URL... urls) {
            // Create URL object
            URL url = createUrl(BASE_URL);

            // Perform HTTP request to the URL and receive a JSON response back
            String jsonResponse = "";
            try {
                jsonResponse = makeHttpRequest(url);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem making the HTTP request.", e);
            }

            // Return the {@link ArrayList<Book>} object as the result fo the {@link BooksAsyncTask}
            return extractFeaturesFromJson(jsonResponse);
        }

        /**
         * Update the screen with the given list of books (which was the result of the
         * {@link BooksAsyncTask}).
         */
        @Override
        protected void onPostExecute(ArrayList<Book> books) {
            if (books == null) {
                return;
            }

            updateUi(books);
        }

        /**
         * Returns new URL object from the given string URL.
         */
        private URL createUrl(String stringUrl) {
            EditText query = (EditText) findViewById(R.id.query);
            int maxResults = 5;

            stringUrl += "?q=";
            stringUrl += query.getText().toString().replace(' ', '+');
            stringUrl += "&maxResults=";
            stringUrl += maxResults;

            Log.i(LOG_TAG, "URL = " + stringUrl);

            URL url = null;
            try {
                url = new URL(stringUrl);
            } catch (MalformedURLException exception) {
                Log.e(LOG_TAG, "Error with creating URL", exception);
                return null;
            }
            return url;
        }

        /**
         * Make an HTTP request to the given URL and return a String as the response.
         */
        private String makeHttpRequest(URL url) throws IOException {
            String jsonResponse = "";

            // If the URL is null, then return early.
            if (url == null) {
                return jsonResponse;
            }

            HttpURLConnection urlConnection = null;
            InputStream inputStream = null;
            try {
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.connect();

                // If the request was successful (response code 200),
                // then read the input stream and parse the response.
                if (urlConnection.getResponseCode() == 200) {
                    inputStream = urlConnection.getInputStream();
                    jsonResponse = readFromStream(inputStream);
                } else {
                    Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
                }
            } catch (IOException e) {
                Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
            return jsonResponse;
        }

        /**
         * Convert the {@link InputStream} into a String which contains the
         * whole JSON response from the server.
         */
        private String readFromStream(InputStream inputStream) throws IOException {
            StringBuilder output = new StringBuilder();
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
                BufferedReader reader = new BufferedReader(inputStreamReader);
                String line = reader.readLine();
                while (line != null) {
                    output.append(line);
                    line = reader.readLine();
                }
            }
            return output.toString();
        }

        /**
         * Return an {@link ArrayList<Book>} object by parsing out information
         * about the list of books from the input booksJSON string.
         */
        private ArrayList<Book> extractFeaturesFromJson(String booksJSON) {
            // If the JSON string is empty or null, then return early.
            if (TextUtils.isEmpty(booksJSON)) {
                return null;
            }

            try {
                JSONObject baseJsonResponse = new JSONObject(booksJSON);
                JSONArray featureArray = baseJsonResponse.getJSONArray("items");

                // If there are results in the features array
                if (featureArray.length() > 0) {
                    ArrayList<Book> books = new ArrayList<>();

                    for (int i = 0; i < featureArray.length(); i++) {
                        JSONObject book = featureArray.getJSONObject(i);
                        JSONObject info = book.getJSONObject("volumeInfo");

                        // Extract out the title, author, publishedDate, and imageUrl
                        String title = info.getString("title");
                        String publishedDate = info.getString("publishedDate");

                        String author = "None"; // default value if no author is provided
                        if (info.has("authors")) {
                            //Extract the first author
                            author = info.getJSONArray("authors").getString(0);
                        }

                        books.add(new Book(title, author, publishedDate));
                    }

                    return books;
                }
            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
            }
            return null;
        }
    }
}
