package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FaceListData implements Serializable{

    @Expose
    public List<FaceData> face_list;
    @Expose
    public String image_width;
    @Expose
    public String image_height;

    public List<FaceData> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<FaceData> face_list) {
        this.face_list = face_list;
    }

    public String getImage_width() {
        return image_width;
    }

    public void setImage_width(String image_width) {
        this.image_width = image_width;
    }

    public String getImage_height() {
        return image_height;
    }

    public void setImage_height(String image_height) {
        this.image_height = image_height;
    }
}
