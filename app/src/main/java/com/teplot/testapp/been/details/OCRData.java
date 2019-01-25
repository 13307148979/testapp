package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class OCRData implements Serializable{

    @Expose
    public String name;
    @Expose
    public String sex;
    @Expose
    public String nation;
    @Expose
    public String birth;
    @Expose
    public String address;
    @Expose
    public String id;
    @Expose
    public String frontimage;
    @Expose
    public String authority;
    @Expose
    public String valid_date;
    @Expose
    public String backimage;
    @Expose
    public String image;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrontimage() {
        return frontimage;
    }

    public void setFrontimage(String frontimage) {
        this.frontimage = frontimage;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getValid_date() {
        return valid_date;
    }

    public void setValid_date(String valid_date) {
        this.valid_date = valid_date;
    }

    public String getBackimage() {
        return backimage;
    }

    public void setBackimage(String backimage) {
        this.backimage = backimage;
    }
}
