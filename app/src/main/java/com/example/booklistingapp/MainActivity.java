package com.example.booklistingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyView = (TextView) findViewById(R.id.empty_text);
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.initLoader(LOADER_ID, null, this);
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

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Book>> loader) {

        mAdapter.clear();

    }



}
