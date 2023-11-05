package com.example.a301groupproject.factory.image;

import android.graphics.Bitmap;
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

//public class Image {
//    private Bitmap bitmap;
//
//    public Image(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }
//
//    public Bitmap getBitmap() {
//        return bitmap;
//    }
//
//    public void setBitmap(Bitmap bitmap) {
//        this.bitmap = bitmap;
//    }
//}
