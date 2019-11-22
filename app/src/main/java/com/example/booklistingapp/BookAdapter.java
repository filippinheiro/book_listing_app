package com.example.booklistingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class BookAdapter extends ArrayAdapter<Book> {
    private TextView mNameTextView;
    private TextView mAuthorTextView;
    private TextView mRatingTextView;

    public BookAdapter(Context context, List<Book> list) {
        super(context, 0, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        View listItemView = convertView;


        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            mNameTextView = (TextView) listItemView.findViewById(R.id.name_text_view);
            mAuthorTextView = (TextView) listItemView.findViewById(R.id.author_text_view);
            mRatingTextView = (TextView) listItemView.findViewById(R.id.rating_text_view);


        }

        //Get the current {@link Book} from the position
        Book currentBook = getItem(position);

        DecimalFormat format = new DecimalFormat("0.0");
        String decimalFormat = null;
        if (currentBook != null) {
            decimalFormat = format.format(currentBook.getAverageRating());

            mNameTextView.setText(currentBook.getTitle());
            mAuthorTextView.setText(currentBook.getAuthor());
            mRatingTextView.setText(decimalFormat);
        }

        return listItemView;
    }

}
