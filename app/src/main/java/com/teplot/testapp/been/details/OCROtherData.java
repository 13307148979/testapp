package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class OCROtherData implements Serializable{

    @Expose
    public String item;
    @Expose
    public String itemstring;
    @Expose
    public List<WeiZhiData> itemcoord;
    @Expose
    public String itemconf;
    @Expose
    public String address;

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getItemstring() {
        return itemstring;
    }

    public void setItemstring(String itemstring) {
        this.itemstring = itemstring;
    }

    public List<WeiZhiData> getItemcoord() {
        return itemcoord;
    }

    public void setItemcoord(List<WeiZhiData> itemcoord) {
        this.itemcoord = itemcoord;
    }

    public String getItemconf() {
        return itemconf;
    }

    public void setItemconf(String itemconf) {
        this.itemconf = itemconf;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
