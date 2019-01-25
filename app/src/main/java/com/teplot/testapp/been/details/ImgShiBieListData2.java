package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class ImgShiBieListData2 implements Serializable{

    @Expose
    public List<ImgShiBieData> tag_list;

    public List<ImgShiBieData> getTag_list() {
        return tag_list;
    }

    public void setTag_list(List<ImgShiBieData> tag_list) {
        this.tag_list = tag_list;
    }
}
