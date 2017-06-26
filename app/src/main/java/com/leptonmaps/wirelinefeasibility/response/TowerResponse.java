package com.leptonmaps.wirelinefeasibility.response;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class TowerResponse implements Serializable {
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

        private int status;
        private String mi_prinx;
        private String tower_id;
        private String tower_name;
        private String tower_height;
        private String address;
        private double latitude;
        private double longitude;
        private String owner_name;
        private String distance;
        private Cost CustomerCityCost;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
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

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public Cost getCustomerCityCost() {
            return CustomerCityCost;
        }

        public void setCustomerCityCost(Cost customerCityCost) {
            CustomerCityCost = customerCityCost;
        }

        public class Cost implements Serializable{
            private String CustomerCity;
            private String CustomerState;
            private String OSPCost;
            private String IBDCost;
            private String ROWCost;
            private String UniqueFID;
            private String TotalCost;

            public String getCustomerCity() {
                return CustomerCity;
            }

            public void setCustomerCity(String customerCity) {
                CustomerCity = customerCity;
            }

            public String getCustomerState() {
                return CustomerState;
            }

            public void setCustomerState(String customerState) {
                CustomerState = customerState;
            }

            public String getOSPCost() {
                return OSPCost;
            }

            public void setOSPCost(String OSPCost) {
                this.OSPCost = OSPCost;
            }

            public String getIBDCost() {
                return IBDCost;
            }

            public void setIBDCost(String IBDCost) {
                this.IBDCost = IBDCost;
            }

            public String getROWCost() {
                return ROWCost;
            }

            public void setROWCost(String ROWCost) {
                this.ROWCost = ROWCost;
            }

            public String getUniqueFID() {
                return UniqueFID;
            }

            public void setUniqueFID(String uniqueFID) {
                UniqueFID = uniqueFID;
            }

            public String getTotalCost() {
                return TotalCost;
            }

            public void setTotalCost(String totalCost) {
                TotalCost = totalCost;
            }
        }

    }

}
