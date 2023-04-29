package com.example.android.googlebooksudacity;


import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };
    private String mTitle;
    private String mAuthors;
    private String mUrl;

    public Book(String title, String authors, String url) {
        mTitle = title;
        mAuthors = authors;
        mUrl = url;
    }

    protected Book(Parcel in) {
        mTitle = in.readString();
        mAuthors = in.readString();
        mUrl = in.readString();
    }

    public String getmAuthors() {
        return mAuthors;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mAuthors);
        dest.writeString(mUrl);
    }
}