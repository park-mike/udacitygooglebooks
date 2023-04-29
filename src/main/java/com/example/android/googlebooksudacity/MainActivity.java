package com.example.android.googlebooksudacity;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String APIurl = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final int BOOK_LOADER_NUMBER = 1;
    public ArrayList<Book> books;
    private String totalSearch = "";
    private BookAdapter bookAdapter;
    private TextView mEmptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView bookListView = (ListView) findViewById(R.id.bookList);
        bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(bookAdapter);
        mEmptyTextView = (TextView) findViewById(R.id.waiting_for_input);
        bookListView.setEmptyView(mEmptyTextView);

        final EditText userSearch = (EditText) findViewById(R.id.search_field);
        Button searchButton = (Button) findViewById(R.id.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String userSearched = userSearch.getText().toString();

                String searchedFor;

                try {
                    searchedFor = java.net.URLEncoder.encode(userSearched, "UTF-8");
                    totalSearch = APIurl + searchedFor;
                    Log.e(LOG_TAG, totalSearch);
                    getData();

                } catch (UnsupportedEncodingException e) {
                    Log.e(LOG_TAG, "Error with creating URL with URLEncoder", e);
                }

            }

        });
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(BOOK_LOADER_NUMBER, null, this);
    }

    public void getData() {


        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network = connect.getActiveNetworkInfo();

        ListView books = (ListView) findViewById(R.id.bookList);
        if (network != null && network.isConnected()) {
            bookAdapter = new BookAdapter(this, new ArrayList<Book>());

            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(BOOK_LOADER_NUMBER, null, this);


            books.setAdapter(bookAdapter);

            books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    Book currentBook = bookAdapter.getItem(position);
                    Uri bookURL = Uri.parse(currentBook.getUrl());
                    Intent websiteIntent = new Intent(Intent.ACTION_VIEW, bookURL);
                    startActivity(websiteIntent);
                }
            });


        } else {
            Log.e(LOG_TAG, "error");
            setErrorView();

        }
    }

    private void setErrorView() {
        View nowLoading = findViewById(R.id.waiting_for_input);
        nowLoading.setVisibility(View.GONE);

        Context context = getApplicationContext();
        String text = "Can not connect to network";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        return new LoaderBook(this, totalSearch);
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        bookAdapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> bookList) {
        View waitingForInput = findViewById(R.id.waiting_for_input);
        waitingForInput.setVisibility(View.GONE);

        if (bookList != null && !bookList.isEmpty()) {
            bookAdapter.addAll(bookList);
        } else {
            View nowLoading = findViewById(R.id.waiting_for_input);
            nowLoading.setVisibility(View.GONE);
            bookAdapter.clear();

        }

    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putString(totalSearch, totalSearch);
        savedInstanceState.putParcelableArrayList("bookList", books);

        // call superclass to save any view hierarchy
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        // Always call the superclass so it can restore the view hierarchy
        super.onRestoreInstanceState(savedInstanceState);
        totalSearch = savedInstanceState.getString(totalSearch);
        if (savedInstanceState != null) {
            books = savedInstanceState.getParcelableArrayList("booklist");
        } else {
            books = new ArrayList<>();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
    }


}



