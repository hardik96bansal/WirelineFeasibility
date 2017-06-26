package com.leptonmaps.wirelinefeasibility.appnetwork;

import android.content.Context;
import android.os.Handler;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import java.util.HashMap;

/**
 * Created by Hardik bansal on 02/06/2017.
 */


public class ReqRespBean {

    private String json;
    private String exception;

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    private String url;
    private String requestmethod = "post";
    private APIType apiType;
    private Handler mHandler;
    private String mimeType = "application/json";
    private int httpResp;
    private Context ctx;
    private HashMap<String, String> header;
    private int httpResponseCode;

    public Context getCtx() {
        return ctx;
    }

    public void setCtx(Context ctx) {
        this.ctx = ctx;
    }

    public int getHttpResp() {
        return httpResp;
    }

    public void setHttpResp(int httpResp) {
        this.httpResp = httpResp;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public HashMap<String, String> getHeader() {
        return header;
    }

    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestmethod() {
        return requestmethod;
    }

    public void setRequestmethod(String requestmethod) {
        this.requestmethod = requestmethod;
    }

    public APIType getApiType() {
        return apiType;
    }

    public void setApiType(APIType apiType) {
        this.apiType = apiType;
    }

    public Handler getmHandler() {
        return mHandler;
    }

    public void setmHandler(Handler mHandler) {
        this.mHandler = mHandler;
    }
}
