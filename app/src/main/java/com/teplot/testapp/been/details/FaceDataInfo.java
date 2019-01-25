package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FaceDataInfo implements Serializable{

    @Expose
    public FaceData face_info;

    public FaceData getFace_info() {
        return face_info;
    }

    public void setFace_info(FaceData face_info) {
        this.face_info = face_info;
    }
}
