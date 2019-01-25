package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class FanYiData implements Serializable{

    @Expose
    public String source_text;
    @Expose
    public String target_text;
    @Expose
    public String x;
    @Expose
    public String y;
    @Expose
    public String width;
    @Expose
    public String height;


    public String getSource_text() {
        return source_text;
    }

    public void setSource_text(String source_text) {
        this.source_text = source_text;
    }

    public String getTarget_text() {
        return target_text;
    }

    public void setTarget_text(String target_text) {
        this.target_text = target_text;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }
}
