package com.example.booklisting;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {

    /**
     * Constant value for the booklist loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int BOOK_LIST_LOADER_ID = 1;

    /**
     * URL for a Google Book API JSON Response
     */
    private static final String GOOGLE_BOOK_LIST_API_URL = "https://www.googleapis.com/books/v1/volumes";

    /**
     * ConnectivityManager Class variable to check network status
     */
    ConnectivityManager connectivityManager;

    /**
     * Adapter providing data to the book listview
     */
    BookListAdapter bookListAdapter;

    /**
     * String variable to store book search query entered by the user
     */
    String searchQuery;

    /**
     * EditText view on the book list screen where user enters search keyword for books
     */
    TextView search_book_edit_text;

    /**
     * Textview to be displayed when the listview have no data
     */
    TextView emptyView;

    /**
     * Button on the book list screen where user enters search keyword for books
     * and press this button
     */
    Button search_book_button;

    /**
     * Progressbar variable which is circular spinner (loading indicator)
     */
    ProgressBar loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the instance of Connectivity Service
        connectivityManager =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //Find reference to the progressbar, the loading spinner
        loadingIndicator = findViewById(R.id.book_list_loading_spinner);

        //Get the LoaderManager object
        LoaderManager loaderManager = getLoaderManager();

        //Initialize the loaderManager Object with its unique ID
        loaderManager.initLoader(BOOK_LIST_LOADER_ID, null, this);

        //Creating an empty ArrayList of type Book class
        final List<Book> books = new ArrayList<>();

        //Find reference to the book query edittext where user can enter search keyword for books
        search_book_edit_text = (TextView) findViewById(R.id.search_book_edit_text);

        //Find reference to the book query button for searching book based on search query
        search_book_button = findViewById(R.id.search_book_button);

        //Setting OnClickListener on search button to make a http request and find relevant books
        search_book_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                search_book_edit_text.clearFocus();

                emptyView.setText("");

               // QueryUtils.hideSoftKeyboard(MainActivity.this);

                bookListAdapter.clear();

                searchQuery = search_book_edit_text.getText().toString();

                if (TextUtils.isEmpty(searchQuery)) {
                    search_book_edit_text.setError("Please enter something");
                } else {
                    showProgressBar();
                    getLoaderManager().restartLoader(BOOK_LIST_LOADER_ID, null, MainActivity.this);
                }
            }
        });

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.book_list_listview);

        // Find a reference to the emptyView TextView
        emptyView = (TextView) findViewById(R.id.emptyView);

        //Set the emptyView for the booklistview
        bookListView.setEmptyView(emptyView);

        // Create a new {@link ArrayAdapter} of books
        bookListAdapter = new BookListAdapter(this, books);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookListAdapter);

        bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //get the url from the corresponding book object into url variable
                String url = books.get(position).getmUrl();

                //create an implicit intent to open the url in the browser
                Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

                //start the activity
                startActivity(implicit);
            }
        });
    }

    /**
     * Method checks whether network is connected or not
     *
     * @param connectivityManager system service of network
     * @return boolean true or false
     */
    private boolean isNetworkConnected(ConnectivityManager connectivityManager) {

        boolean isConnected;

        //Get the active information of the network
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        //Check for the current network state
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int id, Bundle args) {
        String maxBookResults="3";

        Uri baseUri = Uri.parse(GOOGLE_BOOK_LIST_API_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();
        uriBuilder.appendQueryParameter("q", searchQuery);
        uriBuilder.appendQueryParameter("maxResults", maxBookResults);

        // Create a new loader for the given URL
        return new BookListLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {

        // Hide loading indicator because the data has been loaded
        hideProgressBar();

        // Clear the adapter of previous booklist data
        bookListAdapter.clear();

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            bookListAdapter.addAll(books);
        } else {
            emptyView.setText(this.getString(R.string.no_books_found));
        }

        //Setting text for the emptyView TextView as onLoadFinished method is called
        //Check is the internet is connected or not
        if (!isNetworkConnected(connectivityManager)) {
            emptyView.setText(this.getString(R.string.no_internet_connection));
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Clear the adapter of previous booklist data
        bookListAdapter.clear();
    }


    public void showProgressBar() {
        loadingIndicator.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar() {
        loadingIndicator.setVisibility(View.GONE);
    }
}