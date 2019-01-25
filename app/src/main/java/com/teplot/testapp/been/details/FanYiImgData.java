package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class FanYiImgData implements Serializable{

    @Expose
    public int ismatch;
    @Expose
    public int confidence;

    @Expose
    public String session_id;
    @Expose
    public List<FanYiData> image_records;

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public List<FanYiData> getImage_records() {
        return image_records;
    }

    public void setImage_records(List<FanYiData> image_records) {
        this.image_records = image_records;
    }

    public int getIsmatch() {
        return ismatch;
    }

    public void setIsmatch(int ismatch) {
        this.ismatch = ismatch;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }
}
