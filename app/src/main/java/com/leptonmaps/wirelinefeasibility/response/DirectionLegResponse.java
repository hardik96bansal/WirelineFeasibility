package com.leptonmaps.wirelinefeasibility.response;

import android.view.KeyEvent;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 05/06/2017.
 */

public class DirectionLegResponse {
    public Distance getDistance() {
        return distance;
    }

    public void setDistance(Distance distance) {
        this.distance = distance;
    }

    public ArrayList<Steps> getSteps() {
        return steps;
    }

    public void setSteps(ArrayList<Steps> steps) {
        this.steps = steps;
    }

    private Distance distance;
    private ArrayList<Steps> steps;

    public class Distance{
        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        private String text;
        private int value;
    }

    public class Steps{
        public Distance getDistance() {
            return distance;
        }

        public void setDistance(Distance distance) {
            this.distance = distance;
        }

        public Loc getStart_location() {
            return start_location;
        }

        public void setStart_location(Loc start_location) {
            this.start_location = start_location;
        }

        public Loc getEnd_location() {
            return end_location;
        }

        public void setEnd_location(Loc end_location) {
            this.end_location = end_location;
        }

        private Distance distance;
        private Loc start_location;
        private Loc end_location;
        private Pline polyline;

        public Pline getPolyline() {
            return polyline;
        }

        public void setPolyline(Pline polyline) {
            this.polyline = polyline;
        }



        public class Pline{
            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }

            private String points;

        }

        public class Loc{
            double lat;
            double lng;

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }
        }

    }
}
