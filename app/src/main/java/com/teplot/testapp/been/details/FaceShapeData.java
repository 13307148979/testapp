package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FaceShapeData {

    @Expose
    public List<WeiZhiData> face_profile;
    @Expose
    public List<WeiZhiData> left_eye;
    @Expose
    public List<WeiZhiData> right_eye;
    @Expose
    public List<WeiZhiData> left_eyebrow;
    @Expose
    public List<WeiZhiData> right_eyebrow;
    @Expose
    public List<WeiZhiData> mouth;
    @Expose
    public List<WeiZhiData> nose;

    @Expose
    public List<WeiZhiData> face_list;

    public List<WeiZhiData> getFace_list() {
        return face_list;
    }

    public void setFace_list(List<WeiZhiData> face_list) {
        this.face_list = face_list;
    }

    //    public FaceShapeData(List<WeiZhiData> face_profile, List<WeiZhiData> left_eye, List<WeiZhiData> right_eye,
//                         List<WeiZhiData> left_eyebrow, List<WeiZhiData> right_eyebrow, List<WeiZhiData> mouth,
//                         List<WeiZhiData> nose) {
//        this.face_profile = face_profile;
//        this.left_eye = left_eye;
//        this.right_eye = right_eye;
//        this.left_eyebrow = left_eyebrow;
//        this.right_eyebrow = right_eyebrow;
//        this.mouth = mouth;
//        this.nose = nose;
//    }

    public List<WeiZhiData> getFace_profile() {
        return face_profile;
    }

    public void setFace_profile(List<WeiZhiData> face_profile) {
        this.face_profile = face_profile;
    }

    public List<WeiZhiData> getLeft_eye() {
        return left_eye;
    }

    public void setLeft_eye(List<WeiZhiData> left_eye) {
        this.left_eye = left_eye;
    }

    public List<WeiZhiData> getRight_eye() {
        return right_eye;
    }

    public void setRight_eye(List<WeiZhiData> right_eye) {
        this.right_eye = right_eye;
    }

    public List<WeiZhiData> getLeft_eyebrow() {
        return left_eyebrow;
    }

    public void setLeft_eyebrow(List<WeiZhiData> left_eyebrow) {
        this.left_eyebrow = left_eyebrow;
    }

    public List<WeiZhiData> getRight_eyebrow() {
        return right_eyebrow;
    }

    public void setRight_eyebrow(List<WeiZhiData> right_eyebrow) {
        this.right_eyebrow = right_eyebrow;
    }

    public List<WeiZhiData> getMouth() {
        return mouth;
    }

    public void setMouth(List<WeiZhiData> mouth) {
        this.mouth = mouth;
    }

    public List<WeiZhiData> getNose() {
        return nose;
    }

    public void setNose(List<WeiZhiData> nose) {
        this.nose = nose;
    }
}
