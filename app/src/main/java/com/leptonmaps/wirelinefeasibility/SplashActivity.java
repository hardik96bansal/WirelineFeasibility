package com.leptonmaps.wirelinefeasibility;

import android.app.Activity;
import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.leptonmaps.wirelinefeasibility.appnetwork.ReqRespBean;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import com.leptonmaps.wirelinefeasibility.jsonparser.JsonParser;
import com.leptonmaps.wirelinefeasibility.response.DPResponse;
import com.leptonmaps.wirelinefeasibility.response.DirectionLegResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Button b1=(Button)findViewById(R.id.splash_map);

        View.OnClickListener aa=new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i=new Intent(SplashActivity.this,MapsActivity.class);
                startActivity(i);
                /*
                final Gson mGson=new Gson();
                Object obj=null;
                RequestQueue requestQueue= Volley.newRequestQueue(SplashActivity.this);
                String url="https://maps.googleapis.com/maps/api/directions/json?origin=28.497042,%2077.084943&destination=28.49930001,77.08539995999999";
                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {
                                String legs;
                                String object1="";
                                // display response
                                Log.d("Response", response.toString());

                                String status= "";
                                try {
                                    status = response.getString("status");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                if(status.equals("OK")){
                                    try {
                                        legs = response.getJSONArray("routes").getJSONObject(0).getJSONArray("legs").getJSONObject(0).toString();
                                        //object1=legs.getJSONObject(0).getJSONObject("distance").getString("text");
                                        DirectionLegResponse directionLegResponse=(DirectionLegResponse)JsonParser.getJsonToBean(APIType.GET_DIRECTIONS,response.toString());

                                        DirectionLegResponse.Distance distance=directionLegResponse.getDistance();
                                        String text=distance.getText();
                                        Log.e("yolo",text);
                                        if(directionLegResponse.getSteps()!=null){
                                            ArrayList<DirectionLegResponse.Steps> steps= directionLegResponse.getSteps();
                                            for (DirectionLegResponse.Steps tmp : steps) {
                                                if(tmp != null) {
                                                    DirectionLegResponse.Steps.Loc loc=tmp.getStart_location();
                                                    Log.e("location",""+loc.getLat()+","+loc.getLng());
                                                }
                                            }
                                        }
                                        else {
                                            Log.e("yolo","steps is null");

                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    //Log.e("JsonParser","text is "+object1);
                                }
                                else {
                                    Log.e("JsonParser","STATUS NOT OK");
                                }
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error.Response", error.toString());
                            }
                        }
                );

                requestQueue.add(getRequest);
                */
            }
        });
    }
}
