package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FacePersonDetailData implements Serializable{

    @Expose
    public String suc_group;
    @Expose
    public String suc_face;
    @Expose
    public String person_id;
    @Expose
    public String face_id;
    @Expose
    public List<String> group_ids;
    @Expose
    public List<String> face_ids;

    @Expose
    public String person_name;
    @Expose
    public String tag;

    public List<String> getFace_ids() {
        return face_ids;
    }

    public void setFace_ids(List<String> face_ids) {
        this.face_ids = face_ids;
    }

    public String getPerson_name() {
        return person_name;
    }

    public void setPerson_name(String person_name) {
        this.person_name = person_name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getSuc_group() {
        return suc_group;
    }

    public void setSuc_group(String suc_group) {
        this.suc_group = suc_group;
    }

    public String getSuc_face() {
        return suc_face;
    }

    public void setSuc_face(String suc_face) {
        this.suc_face = suc_face;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public List<String> getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(List<String> group_ids) {
        this.group_ids = group_ids;
    }
}
