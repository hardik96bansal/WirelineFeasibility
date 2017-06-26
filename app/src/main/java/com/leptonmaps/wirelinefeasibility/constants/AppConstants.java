package com.leptonmaps.wirelinefeasibility.constants;

import java.util.ArrayList;
import com.leptonmaps.wirelinefeasibility.response.CostResponse;

/**
 * Created by Hardik bansal on 02/06/2017.
 */


public class AppConstants {

    public static final String GET = "get";
    public static final String POST = "post";
    public static final String IS_LOGGED_IN = "IS_LOGGED_IN";
    public static final String USER_ID = "USER_ID";
    public static final String REMIND_ME = "REMIND_ME" ;
    public static final String DATE_FORMAT = "DATE_FORMAT";
    public static final String TOKEN = "TOKEN";

    //public static  String BASE_URL = "http://111.93.46.14:809/";
    public static  String BASE_URL = "http://app.leptonsoftware.com:8487/";

    public static double LAT;
    public static double LNG;
    //public static final String API_KEY = JioBeatPlanner.getContext().getString(R.string.google_places_key);//"AIzaSyBjEZkeQjFYJ77fT1nx9f4uFloa82GVRbE";//"AIzaSyBWfHPxuf4GHDYNjtAm2x-zoj9VC64dTT0";
    public static int TODAY_COUNT = 0;
    //  public static String IMAGE_REQUEST_PATH = AppConfiguration.BASE_SERVER_URL + "StoresApi/getImageFromNAS";

    public static int CUR_BANDWIDTH = 0;
    public static String CUR_BANDWIDTH_UNIT = "";
    public static String CUR_SERVICE = "";
    public static String CUR_SEGMENT = "";
    public static String CUR_CIRCLE = "";
    public static String INTENT_FROM = "";
    public static String CUR_ADDRESS = "N/A";
    public static String CUR_CWW_LOC  = "";
    public static String CUR_DC_LOC = "";
    public static String CUR_LOCATION_END = "";

    public static ArrayList<CostResponse.Result> CUR_COST_RESULT_LIST = new ArrayList<>();
    public static CostResponse.Result CUR_COST_RESULT = null;


    public static void resetCurData(){
        CUR_BANDWIDTH = 0;
        CUR_BANDWIDTH_UNIT = "";
        CUR_SERVICE = "";
        CUR_SEGMENT = "";
        CUR_CIRCLE = "";
        CUR_COST_RESULT_LIST.clear();
        CUR_COST_RESULT = null;

    }

}

