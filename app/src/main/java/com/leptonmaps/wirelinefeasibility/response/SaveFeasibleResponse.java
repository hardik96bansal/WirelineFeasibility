package com.leptonmaps.wirelinefeasibility.response;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class SaveFeasibleResponse {
    private String status;
    private String error_message;
    private Result results;

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

    public Result getResults() {
        return results;
    }

    public void setResults(Result results) {
        this.results = results;
    }

    public class Result{
        private int status; // 0=> means failed, 1=>means saved

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
