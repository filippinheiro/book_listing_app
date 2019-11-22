package com.example.booklistingapp;

import android.text.TextUtils;
import android.util.Log;

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
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")


public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getSimpleName();


    private static URL createUrl(String url) {
        URL Url = null;
        try {
            Url = new URL(url);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problems resolving the URL");
        }
        return Url;
    }

    private static String makeHttpRequest(URL url) throws IOException {

        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection connection = null;
        InputStream inputStream = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(1500);
            connection.setConnectTimeout(1000);
            connection.setRequestMethod("GET");
            connection.connect();

            if (connection.getResponseCode() == 200) {
                inputStream = connection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
        } catch (IOException e) {
            if (connection != null) {
                Log.e(LOG_TAG, "Error response code" + connection.getResponseCode());
            }
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;

    }

    private static String readFromStream(InputStream inputStream) throws IOException {

        StringBuilder builder = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                builder.append(line);
                line = reader.readLine();
            }
        }
        return builder.toString();
    }


    private static List<Book> parseBookJson(String Json) {

        if (TextUtils.isEmpty(Json)) {
            return null;
        }

        List<Book> books = new ArrayList<>();

        try {
            JSONObject root = new JSONObject(Json);
            JSONArray items = root.optJSONArray("items");

            if (items != null) {
                String author = "";
                for (int i = 0; i < items.length(); i++) {
                    JSONObject currentBook = items.optJSONObject(i);
                    if (currentBook != null) {
                        JSONObject volumeInfo = currentBook.optJSONObject("volumeInfo");
                        String title = null;
                        if (volumeInfo != null) {
                            title = volumeInfo.optString("title");
                        }
                        JSONArray authors;
                        if (volumeInfo != null) {
                            authors = volumeInfo.optJSONArray("authors");
                            if (authors != null) {
                                author = authors.optString(i);
                            }
                        }
                        double rating = 0;
                        if (volumeInfo != null) {
                            rating = volumeInfo.optDouble("averageRating");
                        }

                        Book book = new Book(title, author, rating);
                        if (!books.isEmpty() && !books.contains(book)) {
                            books.add(book);
                        } else if (books.isEmpty()) {
                            books.add(book);
                        }
                    }

                    Log.i(LOG_TAG, "Average Rating: " + books.get(i).getAverageRating() + "Author: " + books.get(i).getAuthor());
                    //Log.i(LOG_TAG, Json);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }

        public static List<Book> fetchBookData(String url) {
        URL Url = createUrl(url);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(Url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return parseBookJson(jsonResponse);
    }
}
