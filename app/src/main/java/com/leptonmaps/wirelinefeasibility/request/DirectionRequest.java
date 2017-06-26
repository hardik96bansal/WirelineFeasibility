package com.leptonmaps.wirelinefeasibility.request;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.leptonmaps.wirelinefeasibility.R;
import com.leptonmaps.wirelinefeasibility.SplashActivity;
import com.leptonmaps.wirelinefeasibility.appnetwork.ReqRespBean;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import com.leptonmaps.wirelinefeasibility.jsonparser.JsonParser;
import com.leptonmaps.wirelinefeasibility.response.DirectionLegResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;

/**
 * Created by Hardik bansal on 05/06/2017.
 */

public class DirectionRequest  {
    Context ctx;
    double fromLat,fromLng,toLat,toLng;
    String url;
    RequestQueue requestQueue;
    android.os.Handler mHandler;
    DialogInterface.OnCancelListener mOnCancelListener;
    String result="";

    public DirectionRequest(Context ctx, double fromLat, double fromLng, double toLat, double toLng, DialogInterface.OnCancelListener mOnCancelListener, android.os.Handler mHandler){
        this.ctx=ctx;
        this.fromLat=fromLat;
        this.fromLng=fromLng;
        this.toLat=toLat;
        this.toLng=toLng;
        this.mOnCancelListener=mOnCancelListener;
        this.mHandler=mHandler;
        requestQueue= Volley.newRequestQueue(ctx);
    }

    public void getResponse(){
        url="https://maps.googleapis.com/maps/api/directions/json?origin="+fromLat+","+fromLng+"&destination="+toLat+","+toLng+"&mode=walking";

        String aa;

        class TempRequest extends AsyncTask<String,Integer,String> {
            ProgressDialog mProgressDialog;
            String resp="";
            @Override
            protected void onPreExecute() {
                mProgressDialog.show();
                Log.d("url",url);

                try {
                    if(mProgressDialog != null)
                        mProgressDialog.show();
                    Log.d("msgd","messagedialog is shown");
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("msgd","messagedialog is null");
                }
                super.onPreExecute();
            }
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            public void initProgressDialog(Context context, DialogInterface.OnCancelListener mOnCancelListener){
                mProgressDialog = new ProgressDialog(context, R.style.progressDialogTheme);
                mProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setOnCancelListener(mOnCancelListener);
                //mProgressDialog.setMessage("Please wait...");
                //ProgressBar spinner = new android.widget.ProgressBar(context,null,android.R.attr.progressBarStyle);
                //spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            @Override
            protected String doInBackground(String... strings) {


                JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                        new Response.Listener<JSONObject>()
                        {
                            @Override
                            public void onResponse(JSONObject response) {

                                // display response
                                Log.d("Response", response.toString());
                                JSONObject jo=new JSONObject();
                                result=response.toString();
                                Log.d("resp", result);



                                ReqRespBean result=new ReqRespBean();
                                result.setmHandler(mHandler);
                                result.setApiType(APIType.GET_DIRECTIONS);
                                result.setJson(response.toString());
                                Message message = new Message();
                                message.obj = result;
                                result.getmHandler().sendMessage(message);

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
                Log.d("respxxx", resp);
                return resp;
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    if(mProgressDialog != null) {
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                        Log.d("msgd","messagedialog is removed");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        Log.d("a","a");
        TempRequest tempRequest=new TempRequest();
        tempRequest.initProgressDialog(ctx,mOnCancelListener);
        tempRequest.execute();

        Log.d("b","b");

        Log.d("getresponse",result);
        Log.d("b","b");

    }
}
