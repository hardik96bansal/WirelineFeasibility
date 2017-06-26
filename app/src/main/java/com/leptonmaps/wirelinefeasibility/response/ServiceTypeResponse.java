package com.leptonmaps.wirelinefeasibility.response;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class ServiceTypeResponse {
    private String status;
    private String error_message;
    private ArrayList<String> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getError_message() {
        return error_message;
    }

    public void setError_message(String error_message) {
        this.error_message = error_message;
    }

    public ArrayList<String> getResults() {
        return results;
    }

    public void setResults(ArrayList<String> results) {
        this.results = results;
    }
}
