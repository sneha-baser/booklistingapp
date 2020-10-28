package com.example.booklisting;


import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by aviator on 03/01/18.
 */

public class BookListLoader extends AsyncTaskLoader<List<Book>> {

    /**
     * Tag for log messages
     */
    private static final String LOG_TAG = BookListLoader.class.getName();

    /**
     * Query URL
     */
    private String mUrl;

    /**
     * Constructs a new {@link BookListLoader}.
     *
     * @param context of the activity
     * @param url     to load data from
     */
    public BookListLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    public BookListLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Book> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        // Perform the network request, parse the response, and extract a list of books.
        List<Book> books = QueryUtils.fetchBookListData(mUrl);
        return books;
    }
}