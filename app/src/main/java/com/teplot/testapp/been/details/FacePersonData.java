package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FacePersonData implements Serializable{

    @Expose
    public int group_ids;

    @Expose
    public String person_id;
    @Expose
    public String image_list;
    @Expose
    public String person_name;
    @Expose
    public String tag;

    public int getGroup_ids() {
        return group_ids;
    }

    public void setGroup_ids(int group_ids) {
        this.group_ids = group_ids;
    }

    public String getPerson_id() {
        return person_id;
    }

    public void setPerson_id(String person_id) {
        this.person_id = person_id;
    }

    public String getImage_list() {
        return image_list;
    }

    public void setImage_list(String image_list) {
        this.image_list = image_list;
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
}
