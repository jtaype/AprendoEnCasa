package com.example.aprendoencasa.Model.videostats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistics {

    @SerializedName("viewCount")
    @Expose
    private String viewCount;

    public String getViewCount() {
        return viewCount;
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

}
