package com.leptonmaps.wirelinefeasibility.request;

/**
 * Created by Hardik bansal on 03/06/2017.
 */

public class DPRequest {

    private double cust_latitude;
    private double cust_longitude;
    private double radius;
    private String elementType;
    private String token;

    public String getElementType() {
        return elementType;
    }

    public void setElementType(String elementType) {
        this.elementType = elementType;
    }



    public void setCust_latitude(double cust_latitude) {
        this.cust_latitude = cust_latitude;
    }

    public void setCust_longitude(double cust_longitude) {
        this.cust_longitude = cust_longitude;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setToken(String token) {
        this.token = token;
    }



    public String getToken() {
        return token;
    }



    public double getCust_latitude() {
        return cust_latitude;
    }

    public double getCust_longitude() {
        return cust_longitude;
    }

    public double getRadius() {
        return radius;
    }




}
