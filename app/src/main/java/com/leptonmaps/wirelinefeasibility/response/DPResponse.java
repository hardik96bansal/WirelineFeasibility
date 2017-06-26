package com.leptonmaps.wirelinefeasibility.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hardik bansal on 03/06/2017.
 */

public class DPResponse implements Serializable {
    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    private ArrayList<Result> results;

    public class Result implements Serializable{
        public String getRecordID() {
            return RecordID;
        }

        public void setRecordID(String recordID) {
            RecordID = recordID;
        }

        public String getElementID() {
            return ElementID;
        }

        public void setElementID(String elementID) {
            ElementID = elementID;
        }

        public String getElementType() {
            return ElementType;
        }

        public void setElementType(String elementType) {
            ElementType = elementType;
        }

        public double getLatitude() {
            return Latitude;
        }

        public void setLatitude(double latitude) {
            Latitude = latitude;
        }

        public double getLongitude() {
            return Longitude;
        }

        public void setLongitude(double longitude) {
            Longitude = longitude;
        }

        private String RecordID;
        private String ElementID;
        private String ElementType;
        private double Latitude;
        private double Longitude;

    }
}
