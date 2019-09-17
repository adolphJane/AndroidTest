package com.magicalrice.view.data_graph;

/**
 * Created by Adolph on 2018/6/20.
 */

public class HistogramData {
    private String day,xinqi;
    private int value;
    private boolean liveFlag,questionFlag,videoFlag;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getXinqi() {
        return xinqi;
    }

    public void setXinqi(String xinqi) {
        this.xinqi = xinqi;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isLiveFlag() {
        return liveFlag;
    }

    public void setLiveFlag(boolean liveFlag) {
        this.liveFlag = liveFlag;
    }

    public boolean isQuestionFlag() {
        return questionFlag;
    }

    public void setQuestionFlag(boolean questionFlag) {
        this.questionFlag = questionFlag;
    }

    public boolean isVideoFlag() {
        return videoFlag;
    }

    public void setVideoFlag(boolean videoFlag) {
        this.videoFlag = videoFlag;
    }
}
