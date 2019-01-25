package com.teplot.testapp.been.details;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ImgData implements Serializable{

    @Expose
    public String title;
    @Expose
    public Bitmap bitmap;

    public ImgData(String title, Bitmap bitmap) {
        this.title = title;
        this.bitmap = bitmap;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
