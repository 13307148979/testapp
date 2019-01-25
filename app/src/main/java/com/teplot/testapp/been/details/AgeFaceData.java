package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

public class AgeFaceData implements Serializable{


    @Expose
    public WeiZhiData source_face;
    @Expose
    public WeiZhiData target_face;
    @Expose
    public String score;

    @Expose
    public String similarity;

    @Expose
    public List<String> person_ids;
    @Expose
    public int fail_flag;


    public List<String> getPerson_ids() {
        return person_ids;
    }

    public void setPerson_ids(List<String> person_ids) {
        this.person_ids = person_ids;
    }

    public WeiZhiData getSource_face() {
        return source_face;
    }

    public String getSimilarity() {
        return similarity;
    }

    public void setSimilarity(String similarity) {
        this.similarity = similarity;
    }

    public void setSource_face(WeiZhiData source_face) {
        this.source_face = source_face;
    }

    public WeiZhiData getTarget_face() {
        return target_face;
    }

    public void setTarget_face(WeiZhiData target_face) {
        this.target_face = target_face;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public int getFail_flag() {
        return fail_flag;
    }

    public void setFail_flag(int fail_flag) {
        this.fail_flag = fail_flag;
    }
}
