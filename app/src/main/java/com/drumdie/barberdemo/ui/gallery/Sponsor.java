package com.drumdie.barberdemo.ui.gallery;

import android.os.Parcel;
import android.os.Parcelable;

import java.net.URL;

public class Sponsor implements Parcelable {
    private String name;
    private String url;
    private int imageId;


    public Sponsor(String name, String url, int imageId) {
        this.name = name;
        this.url = url;
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    protected Sponsor(Parcel in) {
        name = in.readString();
        url = in.readString();
        imageId = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeInt(imageId);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Sponsor> CREATOR = new Parcelable.Creator<Sponsor>() {
        @Override
        public Sponsor createFromParcel(Parcel in) {
            return new Sponsor(in);
        }

        @Override
        public Sponsor[] newArray(int size) {
            return new Sponsor[size];
        }
    };
}