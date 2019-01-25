package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ImgShiBieListData5 implements Serializable{

    @Expose
    public List<ImgShiBieData> scene_list;
    @Expose
    public List<ImgShiBieData> object_list;
    @Expose
    public List<ImgShiBieData> tag_list;
    @Expose
    public int topk;


    public List<ImgShiBieData> getObject_list() {
        return object_list;
    }

    public List<ImgShiBieData> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<ImgShiBieData> tag_list) {
        this.tag_list = tag_list;
    }

    public void setObject_list(List<ImgShiBieData> object_list) {
        this.object_list = object_list;
    }

    public List<ImgShiBieData> getScene_list() {
        return scene_list;
    }

    public void setScene_list(List<ImgShiBieData> scene_list) {
        this.scene_list = scene_list;
    }

    public int getTopk() {
        return topk;
    }

    public void setTopk(int topk) {
        this.topk = topk;
    }
}
