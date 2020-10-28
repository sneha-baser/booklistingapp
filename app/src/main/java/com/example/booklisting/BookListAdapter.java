package com.example.booklisting;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

/**
 * Created by aviator on 03/01/18.
 */

/**
 * An {@link BookListAdapter} knows how to create a list item layout for each book
 * in the data source (a list of {@link Book} objects).
 * <p>
 * These list item layouts will be provided to an adapter view like ListView
 * to be displayed to the user.
 */
public class BookListAdapter extends ArrayAdapter<Book> {

    /**
     * Constructs a new {@link BookListAdapter} Adapter Object.
     *
     * @param context of the app
     * @param books   is the list of books, which is the data source of the adapter
     */
    public BookListAdapter(@NonNull Context context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        //Check if the list item view exists that can be reused,
        //otherwise if convertview is null inflate the new listitem layout
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).
                    inflate(R.layout.book_list_item, parent, false);
        }

        Book currentBook = getItem(position);

        TextView book_title = (TextView) listItemView.findViewById(R.id.book_title);

        book_title.setText(currentBook.getBook_title());

        TextView authors_name = (TextView) listItemView.findViewById(R.id.authors_name);

        authors_name.setText(currentBook.getAuthorList());

        return listItemView;
    }
}