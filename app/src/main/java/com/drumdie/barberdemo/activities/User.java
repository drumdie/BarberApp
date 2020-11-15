package com.drumdie.barberdemo.activities;

import android.os.Parcel;
import android.os.Parcelable;

public class User   {
    public String fullName;
    public String email;

    public User(){}

    public User(String fullName, String email) {
        this.fullName = fullName;
        this.email = email;
    }
}