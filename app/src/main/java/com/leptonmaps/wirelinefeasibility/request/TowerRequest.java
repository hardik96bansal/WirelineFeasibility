package com.leptonmaps.wirelinefeasibility.request;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class TowerRequest {
    private double cust_latitude;
    private double cust_longitude;
    private String token;

    public double getCust_latitude() {
        return cust_latitude;
    }

    public void setCust_latitude(double cust_latitude) {
        this.cust_latitude = cust_latitude;
    }

    public double getCust_longitude() {
        return cust_longitude;
    }

    public void setCust_longitude(double cust_longitude) {
        this.cust_longitude = cust_longitude;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}