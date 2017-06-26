package com.leptonmaps.wirelinefeasibility.request;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class CostRequest{

    private double cust_latitude;
    private double cust_longitude;
    private int bandwidth;
    private String service_type;
    private String bandwidth_unit;
    private String circle;
    private String business_segment;
    private String cww_location;
    private String dc_location;
    private String location_end;

    public String getBusiness_segment() {
        return business_segment;
    }

    public void setBusiness_segment(String business_segment) {
        this.business_segment = business_segment;
    }

    public String getCww_location() {
        return cww_location;
    }

    public void setCww_location(String cww_location) {
        this.cww_location = cww_location;
    }

    public String getDc_location() {
        return dc_location;
    }

    public void setDc_location(String dc_location) {
        this.dc_location = dc_location;
    }

    public String getLocation_end() {
        return location_end;
    }

    public void setLocation_end(String location_end) {
        this.location_end = location_end;
    }

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

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getBandwidth_unit() {
        return bandwidth_unit;
    }

    public void setBandwidth_unit(String bandwidth_unit) {
        this.bandwidth_unit = bandwidth_unit;
    }

    public String getCircle() {
        return circle;
    }

    public void setCircle(String circle) {
        this.circle = circle;
    }
}
