package com.example.booklistingapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;
import java.util.List;

public class BookLoader extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = BookLoader.class.getSimpleName();
    private String mUrl;

    public BookLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
        Log.i(LOG_TAG, "Start loading");
    }

    @Nullable
    @Override
    public List<Book> loadInBackground() {
        if(mUrl == null) {
            return null;
        }

        Log.i(LOG_TAG, "Loading in backgoround");

        return QueryUtils.fetchBookData(mUrl);
    }
}
