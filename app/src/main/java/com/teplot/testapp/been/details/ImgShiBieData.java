package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ImgShiBieData implements Serializable{

    @Expose
    public String text;

    @Expose
    public String tag_name;
    @Expose
    public int tag_confidence;
    @Expose
    public String tag_confidence_f;

    @Expose
    public boolean fuzzy;
    @Expose
    public String confidence;

    @Expose
    public boolean food;

    @Expose
    public String label_confd;
    @Expose
    public int label_id;


    public String getTag_confidence_f() {
        return tag_confidence_f;
    }

    public void setTag_confidence_f(String tag_confidence_f) {
        this.tag_confidence_f = tag_confidence_f;
    }

    public boolean isFood() {
        return food;
    }

    public void setFood(boolean food) {
        this.food = food;
    }

    public String getLabel_confd() {
        return label_confd;
    }

    public void setLabel_confd(String label_confd) {
        this.label_confd = label_confd;
    }

    public int getLabel_id() {
        return label_id;
    }

    public void setLabel_id(int label_id) {
        this.label_id = label_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public int getTag_confidence() {
        return tag_confidence;
    }

    public boolean isFuzzy() {
        return fuzzy;
    }

    public void setFuzzy(boolean fuzzy) {
        this.fuzzy = fuzzy;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public void setTag_confidence(int tag_confidence) {
        this.tag_confidence = tag_confidence;
    }
}
