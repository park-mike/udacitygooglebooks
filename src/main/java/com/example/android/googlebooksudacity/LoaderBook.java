package com.example.android.googlebooksudacity;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;


public class LoaderBook extends AsyncTaskLoader<List<Book>> {

    private static final String LOG_TAG = LoaderBook.class.getName();

    private String mURL;

    public LoaderBook(Context context, String url) {
        super(context);
        mURL = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "loading");
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {
        if (mURL == null) {
            return null;
        }

        return API.bookSource(mURL);

    }


}
