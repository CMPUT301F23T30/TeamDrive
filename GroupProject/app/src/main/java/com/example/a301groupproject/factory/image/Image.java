package com.example.a301groupproject.factory.image;

import android.net.Uri;

/**
 * An image object with an attribute: URL.
 */
public class Image {

    private String imageUrl;

    /**
     * Constructor for image class
     *
     * @param imageUrl image url
     */
    public Image(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * get image url
     *
     * @return image url
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * Set image url
     *
     * @param imageUrl image url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

