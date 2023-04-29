package com.example.android.googlebooksudacity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Context context, List<Book> bookList) {
        super(context, 0, bookList);
    }

    @Override
    public View getView(int position, View currentView, ViewGroup parent) {
        View listItemView = currentView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_layout, parent, false);
        }

        Book currentBook = getItem(position);

        TextView authors = (TextView) listItemView.findViewById(R.id.authors);
        String authorsName = currentBook.getmAuthors();
        authors.setText(authorsName);

        TextView title = (TextView) listItemView.findViewById(R.id.title);
        String titleText = currentBook.getmTitle();
        title.setText(titleText);

        return listItemView;

    }
}
