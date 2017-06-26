package com.leptonmaps.wirelinefeasibility.request;

import org.jetbrains.annotations.NotNull;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class SaveSFData {

    @NotNull
    private String username;
    @NotNull
    private String device_id;
    @NotNull
    private String sfa_id;
    @NotNull
    private double cust_latitude;
    @NotNull
    private double cust_longitude;
    @NotNull
    private int bandwidth;
    @NotNull
    private String bandwidth_unit;
    @NotNull
    private String circle;
    @NotNull
    private String service_type;
    @NotNull
    private String contract_period;
    @NotNull
    private String tower_id;

    private String mi_prinx;
    private double latitude;
    private double longitude;

    @NotNull
    private int Rank;

    private String SalesCapex;
    private String NetworkCapex;
    private String OTC;
    private String ARC;

    @NotNull
    private String Mast;

    private String Fresnel_Mast;
    private String MastHghtFinal;
    private String Remark;
    private String Source;

    @NotNull
    private String Result;

    private String BuildingID;
    private double BuildingLat;
    private double BuildingLng;
    private String BuildingStatus;
    private String StatusCode;

    private float distance;
    private String business_segment;

    private String cww_location;
    private String dc_location;
    private String location_end;

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

    public String getBusiness_segment() {
        return business_segment;
    }

    public void setBusiness_segment(String business_segment) {
        this.business_segment = business_segment;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getSfa_id() {
        return sfa_id;
    }

    public void setSfa_id(String sfa_id) {
        this.sfa_id = sfa_id;
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

    public String getService_type() {
        return service_type;
    }

    public void setService_type(String service_type) {
        this.service_type = service_type;
    }

    public String getContract_period() {
        return contract_period;
    }

    public void setContract_period(String contract_period) {
        this.contract_period = contract_period;
    }

    public String getTower_id() {
        return tower_id;
    }

    public void setTower_id(String tower_id) {
        this.tower_id = tower_id;
    }

    public String getMi_prinx() {
        return mi_prinx;
    }

    public void setMi_prinx(String mi_prinx) {
        this.mi_prinx = mi_prinx;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getRank() {
        return Rank;
    }

    public void setRank(int rank) {
        Rank = rank;
    }

    public String getSalesCapex() {
        return SalesCapex;
    }

    public void setSalesCapex(String salesCapex) {
        SalesCapex = salesCapex;
    }

    public String getNetworkCapex() {
        return NetworkCapex;
    }

    public void setNetworkCapex(String networkCapex) {
        NetworkCapex = networkCapex;
    }

    public String getOTC() {
        return OTC;
    }

    public void setOTC(String OTC) {
        this.OTC = OTC;
    }

    public String getARC() {
        return ARC;
    }

    public void setARC(String ARC) {
        this.ARC = ARC;
    }

    public String getMast() {
        return Mast;
    }

    public void setMast(String mast) {
        Mast = mast;
    }

    public String getFresnel_Mast() {
        return Fresnel_Mast;
    }

    public void setFresnel_Mast(String fresnel_Mast) {
        Fresnel_Mast = fresnel_Mast;
    }

    public String getMastHghtFinal() {
        return MastHghtFinal;
    }

    public void setMastHghtFinal(String mastHghtFinal) {
        MastHghtFinal = mastHghtFinal;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSource() {
        return Source;
    }

    public void setSource(String source) {
        Source = source;
    }

    public String getResult() {
        return Result;
    }

    public void setResult(String result) {
        Result = result;
    }

    public String getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(String buildingID) {
        BuildingID = buildingID;
    }

    public double getBuildingLat() {
        return BuildingLat;
    }

    public void setBuildingLat(double buildingLat) {
        BuildingLat = buildingLat;
    }

    public double getBuildingLng() {
        return BuildingLng;
    }

    public void setBuildingLng(double buildingLng) {
        BuildingLng = buildingLng;
    }

    public String getBuildingStatus() {
        return BuildingStatus;
    }

    public void setBuildingStatus(String buildingStatus) {
        BuildingStatus = buildingStatus;
    }

    public String getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(String statusCode) {
        StatusCode = statusCode;
    }
}
