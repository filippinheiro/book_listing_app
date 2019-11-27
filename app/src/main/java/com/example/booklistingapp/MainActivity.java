package com.example.booklistingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*

Project for the Google Android Developing course
@author Filipe Pinheiro

 */

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {


    private final static int LOADER_ID = 0;
    private TextView mEmptyView;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    BookAdapter mAdapter;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyView = (TextView) findViewById(R.id.empty_text);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_circular);
        LoaderManager loaderManager = getSupportLoaderManager();
        if(isConnected()) loaderManager.initLoader(LOADER_ID, null, this);
        else {mEmptyView.setText(getResources().getString(R.string.no_connection)); mProgressBar.setVisibility(View.GONE);}
        //Finding the reference to the ListView
        ListView booksListView = (ListView) findViewById(R.id.list);
        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        booksListView.setEmptyView(mEmptyView);
        booksListView.setAdapter(mAdapter);


    }

    @NonNull
    @Override
    public Loader<List<Book>> onCreateLoader(int id, @Nullable Bundle args) {
        String url = "https://www.googleapis.com/books/v1/volumes?q=android&maxResults=10";
        return new BookLoader(this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Book>> loader, List<Book> data) {

        mAdapter.clear();

        if(data != null && !data.isEmpty()) {
            mAdapter.addAll(data);
        }

        Log.i(LOG_TAG, "Loading finished");
        mEmptyView.setText(R.string.empty_text);
        mProgressBar.setVisibility(View.GONE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

        mAdapter.clear();

    }

    private boolean isConnected() {
        Log.i(LOG_TAG, "Checking connection");
        ConnectivityManager cm =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = null;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
        }
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        Log.i(LOG_TAG, isConnected ? "OK" : "Not connected");
        return isConnected;
    }
}
