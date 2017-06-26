package com.leptonmaps.wirelinefeasibility.request;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class SummaryRequest {
    private String username;
    private int filter_type;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getFilter_type() {
        return filter_type;
    }

    public void setFilter_type(int filter_type) {
        this.filter_type = filter_type;
    }
}
