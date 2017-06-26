package com.leptonmaps.wirelinefeasibility.jsonparser;

import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import com.leptonmaps.wirelinefeasibility.enums.LayerType;
import com.leptonmaps.wirelinefeasibility.response.CostResponse;
import com.leptonmaps.wirelinefeasibility.response.CountResponse;
import com.leptonmaps.wirelinefeasibility.response.DPResponse;
import com.leptonmaps.wirelinefeasibility.response.DirectionLegResponse;
import com.leptonmaps.wirelinefeasibility.response.SaveFeasibleResponse;
import com.leptonmaps.wirelinefeasibility.response.ServiceTypeResponse;
import com.leptonmaps.wirelinefeasibility.response.SummaryResponse;
import com.leptonmaps.wirelinefeasibility.response.TokenResponse;
import com.leptonmaps.wirelinefeasibility.response.TowerResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */


public class JsonParser {

    private static final String TAG = JsonParser.class.getSimpleName();

    public static JSONObject getBeanToJson(Object beanObj){
        Log.d(TAG, "Enter: getBeanToJson()");
        JSONObject jsonObject = null;
        if(beanObj == null)return jsonObject;
        Gson gson = new Gson();

        try {
            jsonObject = new JSONObject(gson.toJson(beanObj));
        } catch (JSONException e) {
            Log.i(TAG,"getBeanToJson():"+e);
        }
        gson = null;
        Log.d(TAG, "Exit:getBeanToJson()");
        return jsonObject;
    }

    public static JSONArray getBeanToJSONArray(Object beanObj) throws JSONException {
        Log.d(TAG,"Enter: getBeanToJson()");
        JSONArray jsonArray = null;
        String jsontxt = "";
        if(beanObj == null)return jsonArray;

        Gson gson =  new Gson();
        try {
            jsontxt = gson.toJson(beanObj);
            jsonArray = new JSONArray(jsontxt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        gson = null;
        Log.d(TAG, "Exit:getBeanToJSONArray()");
        return jsonArray;
    }

    public static Object getJsonToBean(APIType apiType, String json){
        Log.d(TAG,"Enter getJsonToBean():");
        Object obj = null;
        if(apiType == null || json == null || json.length() == 0)return obj;
        Gson mGson =  new Gson();
        try {
            switch (apiType){
                case LOGIN:
                    obj = mGson.fromJson(json, TokenResponse.class);
                    break;
                case GET_TOWERS:
                    obj = mGson.fromJson(json, TowerResponse.class);
                    break;
                case GET_SERVICE_TYPE:
                    break;
                case GET_CIRCLE_TYPE:
                    obj = mGson.fromJson(json, ServiceTypeResponse.class);
                    break;
                case GET_COST:
                    obj = mGson.fromJson(json, CostResponse.class);
                    break;
                case SAVE_FEASIBILE_DATA:
                    obj = mGson.fromJson(json, SaveFeasibleResponse.class);
                    break;
                case GET_SUMMARY:
                    obj = mGson.fromJson(json, SummaryResponse.class);
                    break;
                case GET_SUMMARY_COUNT:
                    obj = mGson.fromJson(json, CountResponse.class);
                    break;

                case GET_DP:
                    obj = mGson.fromJson(json, DPResponse.class);
                    break;
                case GET_DIRECTIONS:
                    String legs;
                    JSONObject res=new JSONObject(json);
                    Log.e("jsonparser res",res.toString());
                    legs = res.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).toString();
                    Log.e("lllllll",legs);
                    obj= mGson.fromJson(legs,DirectionLegResponse.class);
                    break;
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d(TAG,"Exit getJsonToBean():");
        return  obj;
    }

    public static ArrayList<LatLng> parseGeomData(String json, LayerType Type) {
        String[] arr = {};
        ArrayList<LatLng> mGeomBeanList = new ArrayList<LatLng>();
        LatLng mGeomBean = null;
        String s = json;
        switch (Type) {
            case POINT:
                try {
                    s = s.substring(s.indexOf("(") + 1);
                    s = s.substring(0, s.indexOf(")"));
                    arr = s.split(" ");
                    mGeomBean = new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                    mGeomBeanList.add(mGeomBean);
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            case CIRCLE:
                break;
            case POLYLINE:

                try {
                    s = s.substring(s.indexOf("(") + 1);
                    s = s.substring(0, s.indexOf(")"));
                    arr = s.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        String point = arr[i];
                        String[] latlng = point.split(" ");
                        mGeomBean = new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                        mGeomBeanList.add(mGeomBean);
                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;
            case POLYGON:
                try {
                    s = s.substring(s.indexOf("((") + 2);
                    s = s.substring(0, s.indexOf("))") - 1);
                    arr = s.split(",");
                    for (int i = 0; i < arr.length; i++) {
                        String point = arr[i].trim();
                        String[] latlng = point.split(" ");
                        mGeomBean = new LatLng(Double.parseDouble(latlng[1]), Double.parseDouble(latlng[0]));
                        mGeomBeanList.add(mGeomBean);
                    }
                } catch (NumberFormatException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                break;

            default:
                break;
        }

        return mGeomBeanList;

    }

}
