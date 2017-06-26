package com.leptonmaps.wirelinefeasibility.response;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class SummaryResponse {

    private String status;
    private String  error_message;
    private ArrayList<Result> results;

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

    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }

    public class Result{
        private String id;
        private String username;
        private String device_id;
        private String sfa_id;
        private String created_on;
        private String cust_latitude;
        private String cust_longitude;
        private String service_type;
        private int bandwidth;
        private String bandwidth_unit;
        private String contract_period;
        private String circle;
        private String mi_prinx;
        private String tower_id;
        private double latitude;
        private double longitude;
        private float distance;
        private int Rank;
        private float SalesCapex;
        private float NetworkCapex;
        private float OTC;
        private float ARC;
        private String BuildingStatus;
        private String BuildingID;
        private double BuildingLat;
        private double BuildingLng;
        private String Mast;
        private String Fresnel_Mast;
        private String MastHghtFinal;
        private String StatusCode;
        private String Result;
        private String Remark;
        private String Source;
        private float TotalCost;
        private String tower_name;

        public float getTotalCost() {
            return TotalCost;
        }

        public void setTotalCost(float totalCost) {
            TotalCost = totalCost;
        }

        public String getTower_name() {
            return tower_name;
        }

        public void setTower_name(String tower_name) {
            this.tower_name = tower_name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getCreated_on() {
            return created_on;
        }

        public void setCreated_on(String created_on) {
            this.created_on = created_on;
        }

        public String getCust_latitude() {
            return cust_latitude;
        }

        public void setCust_latitude(String cust_latitude) {
            this.cust_latitude = cust_latitude;
        }

        public String getCust_longitude() {
            return cust_longitude;
        }

        public void setCust_longitude(String cust_longitude) {
            this.cust_longitude = cust_longitude;
        }

        public String getService_type() {
            return service_type;
        }

        public void setService_type(String service_type) {
            this.service_type = service_type;
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

        public String getContract_period() {
            return contract_period;
        }

        public void setContract_period(String contract_period) {
            this.contract_period = contract_period;
        }

        public String getCircle() {
            return circle;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public String getMi_prinx() {
            return mi_prinx;
        }

        public void setMi_prinx(String mi_prinx) {
            this.mi_prinx = mi_prinx;
        }

        public String getTower_id() {
            return tower_id;
        }

        public void setTower_id(String tower_id) {
            this.tower_id = tower_id;
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

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public int getRank() {
            return Rank;
        }

        public void setRank(int rank) {
            Rank = rank;
        }

        public float getSalesCapex() {
            return SalesCapex;
        }

        public void setSalesCapex(float salesCapex) {
            SalesCapex = salesCapex;
        }

        public float getNetworkCapex() {
            return NetworkCapex;
        }

        public void setNetworkCapex(float networkCapex) {
            NetworkCapex = networkCapex;
        }

        public float getOTC() {
            return OTC;
        }

        public void setOTC(float OTC) {
            this.OTC = OTC;
        }

        public float getARC() {
            return ARC;
        }

        public void setARC(float ARC) {
            this.ARC = ARC;
        }

        public String getBuildingStatus() {
            return BuildingStatus;
        }

        public void setBuildingStatus(String buildingStatus) {
            BuildingStatus = buildingStatus;
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

        public String getStatusCode() {
            return StatusCode;
        }

        public void setStatusCode(String statusCode) {
            StatusCode = statusCode;
        }

        public String getResult() {
            return Result;
        }

        public void setResult(String result) {
            Result = result;
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
    }
}
