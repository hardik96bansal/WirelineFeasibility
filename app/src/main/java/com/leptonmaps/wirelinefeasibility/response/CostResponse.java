package com.leptonmaps.wirelinefeasibility.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */


public class CostResponse implements Serializable {
    private String status;
    private String error_message;
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

    public class Result implements Serializable{
        private String mi_prinx;
        private String tower_id;
        private String tower_name;
        private String tower_height;
        private String tower_height_unit;
        private String address;
        private double latitude;
        private double longitude;
        private String owner_name;
        private float distance;
        private String distance_unit;
        private String Mast;
        private float TotalCost;
        private String AvgMast;
        private int Rank;
        private  FeasibilityResult FeasibilityLOSResult;

        public float getTotalCost() {
            return TotalCost;
        }

        public void setTotalCost(float totalCost) {
            TotalCost = totalCost;
        }

        public String getAvgMast() {
            return AvgMast;
        }

        public void setAvgMast(String avgMast) {
            AvgMast = avgMast;
        }

        public int getRank() {
            return Rank;
        }

        public void setRank(int rank) {
            Rank = rank;
        }

        public String getMast() {
            return Mast;
        }

        public void setMast(String mast) {
            Mast = mast;
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

        public String getTower_name() {
            return tower_name;
        }

        public void setTower_name(String tower_name) {
            this.tower_name = tower_name;
        }

        public String getTower_height() {
            return tower_height;
        }

        public void setTower_height(String tower_height) {
            this.tower_height = tower_height;
        }

        public String getTower_height_unit() {
            return tower_height_unit;
        }

        public void setTower_height_unit(String tower_height_unit) {
            this.tower_height_unit = tower_height_unit;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
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

        public String getOwner_name() {
            return owner_name;
        }

        public void setOwner_name(String owner_name) {
            this.owner_name = owner_name;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) {
            this.distance = distance;
        }

        public String getDistance_unit() {
            return distance_unit;
        }

        public void setDistance_unit(String distance_unit) {
            this.distance_unit = distance_unit;
        }

        public FeasibilityResult getFeasibilityLOSResult() {
            return FeasibilityLOSResult;
        }

        public void setFeasibilityLOSResult(FeasibilityResult feasibilityLOSResult) {
            FeasibilityLOSResult = feasibilityLOSResult;
        }
    }

    public class FeasibilityResult implements Serializable{
        private String circle;
        private String bandwidth;
        private String serviceType;
        private String MastType;
        private float SalesCapex;
        private float NetworkCapex;
        private float OTC;
        private float  ARC;
        private String ContractPeriod;
        private String CommViable;
        private String BuildingStatus;
        private String BuildingID;
        private double BuildingLat;
        private double BuildingLng;
        private String StatusCode;
        private String Result;
        private String CustBldHgt;
        private String CustAMSLHgt;
        private String TowerAMSLHgt;
        private String TowerHgt;
        private String TowerBldHgt;
        private float Mast;
        private String Fresnel_Mast;
        private String MastHghtFinal;
        private String Remark;
        private String Source;
        private String ElevAng;

        public String getCircle() {
            return circle;
        }

        public void setCircle(String circle) {
            this.circle = circle;
        }

        public String getBandwidth() {
            return bandwidth;
        }

        public void setBandwidth(String bandwidth) {
            this.bandwidth = bandwidth;
        }

        public String getServiceType() {
            return serviceType;
        }

        public void setServiceType(String serviceType) {
            this.serviceType = serviceType;
        }

        public String getMastType() {
            return MastType;
        }

        public void setMastType(String mastType) {
            MastType = mastType;
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

        public String getContractPeriod() {
            return ContractPeriod;
        }

        public void setContractPeriod(String contractPeriod) {
            ContractPeriod = contractPeriod;
        }

        public String getCommViable() {
            return CommViable;
        }

        public void setCommViable(String commViable) {
            CommViable = commViable;
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

        public String getCustBldHgt() {
            return CustBldHgt;
        }

        public void setCustBldHgt(String custBldHgt) {
            CustBldHgt = custBldHgt;
        }

        public String getCustAMSLHgt() {
            return CustAMSLHgt;
        }

        public void setCustAMSLHgt(String custAMSLHgt) {
            CustAMSLHgt = custAMSLHgt;
        }

        public String getTowerAMSLHgt() {
            return TowerAMSLHgt;
        }

        public void setTowerAMSLHgt(String towerAMSLHgt) {
            TowerAMSLHgt = towerAMSLHgt;
        }

        public String getTowerHgt() {
            return TowerHgt;
        }

        public void setTowerHgt(String towerHgt) {
            TowerHgt = towerHgt;
        }

        public String getTowerBldHgt() {
            return TowerBldHgt;
        }

        public void setTowerBldHgt(String towerBldHgt) {
            TowerBldHgt = towerBldHgt;
        }

        public float getMast() {
            return Mast;
        }

        public void setMast(float mast) {
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

        public String getElevAng() {
            return ElevAng;
        }

        public void setElevAng(String elevAng) {
            ElevAng = elevAng;
        }
    }

}
