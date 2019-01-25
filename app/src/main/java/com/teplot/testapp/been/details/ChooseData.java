package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ChooseData implements Serializable{

    @Expose
    public String id;
    @Expose
    public String text;

    public ChooseData() {
    }

    public ChooseData(String id, String text) {
        this.id = id;
        this.text = text;
    }

    public ChooseData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
