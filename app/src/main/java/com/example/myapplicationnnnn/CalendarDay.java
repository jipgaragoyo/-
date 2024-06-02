package com.example.myapplicationnnnn;

import android.net.Uri;

public class CalendarDay {
    private int year;
    private int month;
    private int day;
    private Uri imageUri;

    public CalendarDay(int year, int month, int day, Uri imageUri) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.imageUri = imageUri;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}
