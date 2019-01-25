package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class OCROtherListData implements Serializable{


    @Expose
    public List<OCROtherData> item_list;

    public List<OCROtherData> getItem_list() {
        return item_list;
    }

    public void setItem_list(List<OCROtherData> item_list) {
        this.item_list = item_list;
    }
}
