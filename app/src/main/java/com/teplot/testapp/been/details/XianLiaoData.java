package com.teplot.testapp.been.details;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class XianLiaoData implements Serializable{

    @Expose
    public String session;
    @Expose
    public String answer;
    @Expose
    public String question;

    public XianLiaoData(String answer, String question) {
        this.answer = answer;
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
