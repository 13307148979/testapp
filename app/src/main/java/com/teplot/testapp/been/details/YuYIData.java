package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class YuYIData implements Serializable{

    //情感相关
    @Expose
    public String text;
    @Expose
    public int polar;
    @Expose
    public String confd;


    @Expose
    public int com_type;
    @Expose
    public String com_word;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPolar() {
        return polar;
    }

    public void setPolar(int polar) {
        this.polar = polar;
    }

    public String getConfd() {
        return confd;
    }

    public void setConfd(String confd) {
        this.confd = confd;
    }

    public int getCom_type() {
        return com_type;
    }

    public void setCom_type(int com_type) {
        this.com_type = com_type;
    }

    public String getCom_word() {
        return com_word;
    }

    public void setCom_word(String com_word) {
        this.com_word = com_word;
    }
}
