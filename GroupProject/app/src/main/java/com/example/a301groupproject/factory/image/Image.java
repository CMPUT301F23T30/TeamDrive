package com.example.a301groupproject.factory.image;

import android.net.Uri;

/**
 * An image object with an attribute: URI.
 */
public class Image {

    private Uri imageUri;

    /**
     * Constructor for image class
     *
     * @param imageUri image uri
     */
    public Image(Uri imageUri) {
        this.imageUri = imageUri;
    }

    /**
     * get image uri
     *
     * @return image uri
     */
    public Uri getImageUri() {
        return imageUri;
    }

    /**
     * Set image uri
     *
     * @param imageUri image uri
     */
    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

