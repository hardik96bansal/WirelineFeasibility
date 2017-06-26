package com.leptonmaps.wirelinefeasibility.appnetwork;


import com.leptonmaps.wirelinefeasibility.constants.AppConstants;
import com.leptonmaps.wirelinefeasibility.enums.APIType;

/**
 * Created by Hardik bansal on 02/06/2017.
 */



public class WEBAPI {

    public static String getWEBAPI(APIType apiType) {
        String api = "";
        String separator = AppConstants.BASE_URL.endsWith("/") ? "" : "/";
        switch (apiType) {
            case LOGIN:
                api = AppConstants.BASE_URL + separator + "token";
                break;
            case GET_TOWERS:
                api = AppConstants.BASE_URL + separator + "api/towers/gettowersbylatlng";
                break;
            case GET_SERVICE_TYPE:
                api = AppConstants.BASE_URL + separator + "api/servicetypes/getall";
                break;
            case GET_CIRCLE_TYPE:
                //api = AppConstants.BASE_URL + separator + "api/circles/all";
                api = AppConstants.BASE_URL + separator + "api/circles/getbylatlng";
                break;
            case GET_COST:
                api = AppConstants.BASE_URL + separator + "api/towers/custfeasibletowerscost";
                break;
            case SAVE_FEASIBILE_DATA:
                api = AppConstants.BASE_URL + separator + "api/sfatrackings/save";
                break;
            case GET_SUMMARY_COUNT:
                api = AppConstants.BASE_URL + separator + "api/sfatrackings/count";
                break;
            case GET_SUMMARY:
                api = AppConstants.BASE_URL + separator + "api/sfatrackings/reports";
                break;
            case GET_DP:
                api=AppConstants.BASE_URL + separator + "api/Network/Entities/?";
                break;
        }
        return api;
    }

    public static final String GET = "get";
    public static final String POST = "post";
}

