package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class YuYIListData implements Serializable{

    //情感相关
    @Expose
    public String text;
    @Expose
    public int intent;
    @Expose
    public List<YuYIData> com_tokens;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIntent() {
        return intent;
    }

    public void setIntent(int intent) {
        this.intent = intent;
    }

    public List<YuYIData> getCom_tokens() {
        return com_tokens;
    }

    public void setCom_tokens(List<YuYIData> com_tokens) {
        this.com_tokens = com_tokens;
    }
}
