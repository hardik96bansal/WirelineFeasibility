package com.leptonmaps.wirelinefeasibility.apputil;

import android.content.Context;
import android.content.SharedPreferences;
import com.leptonmaps.wirelinefeasibility.R;
import com.leptonmaps.wirelinefeasibility.constants.AppConstants;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class SharePrefUtil {

    public static boolean clearSharePref(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        return mEditor.commit();
    }

    public static boolean isLoggedIn(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false);
    }

    public static boolean setIsLoggedIn(Context ctx,boolean isLoggedIn){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(AppConstants.IS_LOGGED_IN,isLoggedIn);
        return mEditor.commit();
    }

    public static boolean setUserId(Context ctx, String userId){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(AppConstants.USER_ID,userId);
        return mEditor.commit();
    }
    public static String getUserId(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getString(AppConstants.USER_ID, "");
    }

    public static boolean setRemindMe(Context ctx, boolean remindMe) {
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(AppConstants.REMIND_ME,remindMe);
        return mEditor.commit();
    }
    public static boolean getRemindMe(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(AppConstants.REMIND_ME, false);
    }
    public static String getDateFormat(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getString(AppConstants.DATE_FORMAT, ctx.getString(R.string.dd_mmm_yyyy));
    }
    public static boolean setDateFormat(Context ctx,String dateFormat){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(AppConstants.DATE_FORMAT, dateFormat);
        return mEditor.commit();
    }

   /* public static String getTimeFormat(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        String t = ctx.getString(R.string.time_12h_format);
        return mSharedPreferences.getString(AppConstants.TIME_FORMAT, t);
    }
    public static boolean setTimeFormat(Context ctx,String timeFormat){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(AppConstants.TIME_FORMAT, timeFormat);
        return mEditor.commit();
    }*/

   /* public static boolean getMenuAppTour(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(AppConstants.MENU_APP_TOUR, false);
    }
    public static boolean setMenuAppTour(Context ctx,boolean ticketAppTour){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(AppConstants.MENU_APP_TOUR, ticketAppTour);
        return mEditor.commit();
    }*/

    public static String getToken(Context ctx){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        return mSharedPreferences.getString(AppConstants.TOKEN, "");
    }
    public static boolean setToken(Context ctx,String token){
        SharedPreferences mSharedPreferences = ctx.getSharedPreferences(ctx.getResources().getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(AppConstants.TOKEN, token);
        return mEditor.commit();
    }
}
