package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FaceData implements Serializable{

    @Expose
    public String face_id;
    @Expose
    public int x;
    @Expose
    public int y;
    @Expose
    public int width;
    @Expose
    public int height;
    @Expose
    public int gender;
    @Expose
    public int age;
    @Expose
    public int expression;
    @Expose
    public int beauty;
    @Expose
    public int glass;
    @Expose
    public String pitch;
    @Expose
    public String yaw;
    @Expose
    public String roll;
    @Expose
    public FaceShapeData face_shape;

    @Expose
    public int image_width;
    @Expose
    public int image_height;
    @Expose
    public List<FaceShapeData> face_shape_list;

    public int getImage_width() {
        return image_width;
    }

    public List<FaceShapeData> getFace_shape_list() {
        return face_shape_list;
    }

    public void setFace_shape_list(List<FaceShapeData> face_shape_list) {
        this.face_shape_list = face_shape_list;
    }

    public void setImage_width(int image_width) {
        this.image_width = image_width;
    }

    public int getImage_height() {
        return image_height;
    }

    public void setImage_height(int image_height) {
        this.image_height = image_height;
    }

    public String getFace_id() {
        return face_id;
    }

    public void setFace_id(String face_id) {
        this.face_id = face_id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getExpression() {
        return expression;
    }

    public void setExpression(int expression) {
        this.expression = expression;
    }

    public int getBeauty() {
        return beauty;
    }

    public void setBeauty(int beauty) {
        this.beauty = beauty;
    }

    public int getGlass() {
        return glass;
    }

    public void setGlass(int glass) {
        this.glass = glass;
    }

    public String getPitch() {
        return pitch;
    }

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public String getYaw() {
        return yaw;
    }

    public void setYaw(String yaw) {
        this.yaw = yaw;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public FaceShapeData getFace_shape() {
        return face_shape;
    }

    public void setFace_shape(FaceShapeData face_shape) {
        this.face_shape = face_shape;
    }
}
