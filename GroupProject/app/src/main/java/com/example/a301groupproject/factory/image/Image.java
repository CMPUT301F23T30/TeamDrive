package com.example.a301groupproject.factory.image;

import android.net.Uri;

public class Image {

    private Uri imageUri;

    public Image(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }
}

