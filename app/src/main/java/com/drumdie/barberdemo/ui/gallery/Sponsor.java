package com.drumdie.barberdemo.ui.gallery;

import java.net.URL;

public class Sponsor {
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
}
