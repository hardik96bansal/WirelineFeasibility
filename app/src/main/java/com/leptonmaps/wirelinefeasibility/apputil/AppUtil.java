package com.leptonmaps.wirelinefeasibility.apputil;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.leptonmaps.wirelinefeasibility.LoginActivity;
import com.leptonmaps.wirelinefeasibility.R;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;



/**
 * Created by Hardik bansal on 02/06/2017.
 */


public class AppUtil {

    private static String TAG = "AppUtil";

    /**
     * This function is used to get LepLogger Object.
     * @return LepLogger
     */

    public static Typeface getRobotoRg(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Regular.ttf");
    }
    public static Typeface getRobotoMd(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Medium.ttf");
    }
    public static Typeface getRobotoLt(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-Light.ttf");
    }

    public static Typeface getVodafoneRg(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/VodafoneRg.ttf");
    }
    public static Typeface getVodafoneRgBd(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/VodafoneRgBd.ttf");
    }
    public static Typeface getVodafoneLt(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/VodafoneLt.ttf");
    }

    public static Typeface getIcomoonTypeface(Context ctx){
        return  Typeface.createFromAsset(ctx.getAssets(),"fonts/icomoon.ttf");
    }



    /**
     * This function is used to fetch Application Version String.
     * @param context
     * @return
     */
    public static String getAppVersionString(Context context) {
        int versionNumber = 0;
        String versionName = "";
        try {
            PackageInfo pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionNumber = pinfo.versionCode;
            versionName = pinfo.versionName;
        }catch(Exception e){
        }
        return versionName;
    }

    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        final Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(output);
        final int color = Color.RED;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawOval(rectF, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        bitmap.recycle();
        return output;
    }

    public static boolean isServiceRunning(Context ctx, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) ctx.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }



    public static String convertUTCTimeZone(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(time);
        return (cal.get(Calendar.YEAR) + "/"
                + (cal.get(Calendar.MONTH) + 1) + "/"
                + cal.get(Calendar.DAY_OF_MONTH) + "-"
                + cal.get(Calendar.HOUR_OF_DAY) + ":"
                + cal.get(Calendar.MINUTE) + ":"
                + cal.get(Calendar.SECOND));

    }

    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }
    public static Date getCurrentDate() {
        try {
            SimpleDateFormat outputdateFormat = new SimpleDateFormat("dd-MM-yyyy");
            String modifiedDate = outputdateFormat.format(new Date());
            Date date = (Date) outputdateFormat.parse(modifiedDate);
            return date;
        }catch (Exception e){
            return  new Date();
        }
    }

    /**
     *
     * @param strDate ("yyyy-MM-dd'T'HH:mm")
     * @return
     * @throws ParseException
     */
    public static String getDate(String strDate){
        if(strDate == null)return null;
        SimpleDateFormat outputdateFormat= null;
        Date date = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            date = (Date) dateFormat.parse(strDate);
            outputdateFormat = new SimpleDateFormat("dd-MM-yyyy");
        }catch(Exception e){
            e.printStackTrace();
        }

        return outputdateFormat.format(date.getTime());
    }



    public static String getDateTime(String strDate){
        if(strDate == null)return null;
        Date date = null;
        SimpleDateFormat outputdateFormat = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            date = (Date) dateFormat.parse(strDate);
            outputdateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return outputdateFormat.format(date.getTime());
    }

    public static Date getTimeFromString(String dateTimeString){
        Date mDate = null;
        try {
            if(dateTimeString == null)return mDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            mDate = (Date) dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    public static Date getDateTimeFromString(String dateTimeString){
        Date mDate = null;
        try {
            if(dateTimeString == null)return mDate;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mDate = (Date) dateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mDate;
    }

    /**
     * To animate the marker from one position to another.
     * @param marker
     * @param source
     * @param dest
     */
    public void animateMarker(final Marker marker, final LatLng source, final LatLng dest){
        final Handler handler =  new Handler();
        final long start = SystemClock.uptimeMillis();
        final Interpolator interpolator = new LinearInterpolator();// You can change interpolator like accelerator interpolator
        final long duration = 1600;
        Runnable animator = new Runnable() {
            @Override
            public void run() {
                long elapsed =  SystemClock.uptimeMillis() - start;
                double t = interpolator.getInterpolation((float)elapsed/duration);
                double lat = t*dest.latitude + (1-t)*source.latitude;
                double lng = t*dest.longitude + (1-t)*source.longitude;
                //Set marker position
                marker.setPosition(new LatLng(lat,lng));
                if(t < 1.0){
                    handler.postDelayed(this,16);// typically android device render per frame at 16 milliseconds.
                }
            }
        };
        handler.post(animator);
    }


    public static boolean isRooted() {
        return findBinary("su");
    }

    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = {"/sbin/", "/system/bin/", "/system/xbin/", "/data/local/xbin/",
                    "/data/local/bin/", "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/"};
            for (String where : places) {
                if ( new File( where + binaryName ).exists() ) {
                    found = true;
                    break;
                }
            }
        }
        return found;
    }





    public static String getDeviceID(Context context){
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }



    public static void errorDialog(Context ctx, String title, String msg, DialogInterface.OnClickListener dialgClickListener) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton("OK", dialgClickListener);
            builder.create();
            builder.show();
        }
        catch (Exception e){
            //TODO://
        }

    }

    public static void commentDialog(Context ctx, View view, String title, String msg, DialogInterface.OnClickListener dialgClickListener) {
        try{
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setView(view);

            builder.setPositiveButton("Ok", dialgClickListener);
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.create();
            builder.show();
        }
        catch (Exception e){
            //TODO://
        }

    }



    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
    public static void disableSSLCertificateChecking() {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[] {new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }
            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }
        }
        };

        // Install the all-trusting trust manager
        try{
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        }catch (Exception e){

        }
        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        /*TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }*/
    }


    public static String getApplicationName(Context context) {
        int stringId = context.getApplicationInfo().labelRes;
        return context.getString(stringId);
    }




    public static void sendMail(Context ctx, String to, String subject, String body){
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto",to,null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
            emailIntent.putExtra(Intent.EXTRA_TEXT, body);
            ctx.startActivity(Intent.createChooser(emailIntent, "Send email..."));
        }catch (Exception e){
            //TODO:
        }
    }

    public static void sendSMS(Context ctx, String phone){
        try {
            Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
            smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.setData(Uri.parse("sms:" + phone));
            ctx.startActivity(smsIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isJioChatInstalled(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.jiochat.jiochatapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    public static boolean openJioChat(Context context){
        PackageManager pm=context.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.jiochat.jiochatapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.jiochat.jiochatapp");
            //waIntent.putExtra(Intent.EXTRA_TEXT, text1+coupon+text2);
            context.startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {
            //displayToast("WhatsApp not Installed", Gravity.CENTER, false, Toast.LENGTH_SHORT);
            return false;
        }
        return true;
        /*try {
            Intent mIntent = new Intent(Intent.ACTION_SENDTO);
            mIntent.setPackage("com.jiochat.jiochatapp");
            ctx.startActivity(mIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    public static boolean isWhatsAppInstalled(Context ctx){
        PackageManager pm = ctx.getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            app_installed = true;
        }
        catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed ;
    }

    public static boolean onClickWhatsApp(Context context) {
        PackageManager pm=context.getPackageManager();
        try {
            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");

            PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            //Check if package exists or not. If not then code
            //in catch block will be called
            waIntent.setPackage("com.whatsapp");
            //waIntent.putExtra(Intent.EXTRA_TEXT, text1+coupon+text2);
            context.startActivity(Intent.createChooser(waIntent, "Share with"));
        } catch (PackageManager.NameNotFoundException e) {
            //displayToast("WhatsApp not Installed", Gravity.CENTER, false, Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }


    /*  public static Bitmap getMarkerIcon(final Context context, int drawable, String id,String sequence) {
          View cluster = LayoutInflater.from(context).inflate(R.layout.marker_icon,null);
          TextView tv_marker_text = (TextView) cluster.findViewById(R.id.tv_marker_text);
          tv_marker_text.setText(id);
          TextView tv_sequence = (TextView) cluster.findViewById(R.id.tv_sequence);
          tv_sequence.setText(sequence);
          ImageView iv_marker = (ImageView) cluster.findViewById(R.id.iv_marker);
          iv_marker.setImageResource(drawable);
          cluster.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
          cluster.layout(0, 0, cluster.getMeasuredWidth(),cluster.getMeasuredHeight());
          final Bitmap clusterBitmap = Bitmap.createBitmap(cluster.getMeasuredWidth(),cluster.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
          Canvas canvas = new Canvas(clusterBitmap);
          cluster.draw(canvas);
          return clusterBitmap;
      }
  */
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }





    public static int dp2px(Context context, float dp) {
        //		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 20, context.getResources().getDisplayMetrics());
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int px2dp(Context context, float px){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) dp;
    }

    public static int screenHeight(Activity ctx){
        if(ctx == null)return  0;
        DisplayMetrics metrics1 = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics1);
        return metrics1.heightPixels;
    }
    public static int screenWidth(Activity ctx){
        if(ctx == null)return  0;
        DisplayMetrics metrics1 = new DisplayMetrics();
        ctx.getWindowManager().getDefaultDisplay().getMetrics(metrics1);
        return metrics1.widthPixels;
    }

    public static final int GRANTED = 0;
    public static final int DENIED = 1;
    public static final int BLOCKED = 2;

    public static int getPermissionStatus(Activity activity, String androidPermissionName) {
        int status = -1;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                    status =  BLOCKED;
                }
                status = DENIED;
            }
            status = GRANTED;
        }else{
            status = GRANTED;
        }
        return  status;
    }

    public static boolean isAppInstalled(Context ctx, Intent intent) {
        List<ResolveInfo> list = ctx.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }







    public static final int getColor(Context context, int id) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 23) {
            return ContextCompat.getColor(context, id);
        } else {
            return context.getResources().getColor(id);
        }
    }

    public static float distanceBetween(LatLng srcLatLng, LatLng dstLatLng) {
        Location srcLoc = new Location(LocationManager.GPS_PROVIDER);
        Location dstLoc = new Location(LocationManager.GPS_PROVIDER);

        srcLoc.setLatitude(srcLatLng.latitude);
        srcLoc.setLongitude(srcLatLng.longitude);

        dstLoc.setLatitude(dstLatLng.latitude);
        dstLoc.setLongitude(dstLatLng.longitude);

        return srcLoc.distanceTo(dstLoc);
    }

    public static boolean isValidMail(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }



    public static String getTimeString(Context context, Long time)
    {
        Date dt = new Date(time);
        String dtStr = dt.toString();
        Long now = new Date().getTime();
        Date nowDt = new Date();
        String nowStr = nowDt.toString();

        String date = (String) DateUtils.getRelativeDateTimeString(context, time, DateUtils.MINUTE_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL);
        date = date.split(",")[0];
        return date;
    }

    public static Bitmap getBitmapForFilePath(Context context, String path, boolean isRotate) {
        //isRotate = true;
        int orientation;
        try {
            if (path == null) {
                return null;
            }
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 70;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 2;
            // while (true) {
            // if (width_tmp / 2 < REQUIRED_SIZE
            // || height_tmp / 2 < REQUIRED_SIZE)
            // break;
            // width_tmp /= 2;
            // height_tmp /= 2;
            // scale++;
            // }
            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            Bitmap bm = BitmapFactory.decodeFile(path, o2);
            Bitmap bitmap = bm;
            ExifInterface exif = new ExifInterface(path);
            orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
            if(isRotate){
                orientation  = orientation +90;
            }
            Log.e("ExifInteface .........", "rotation =" + orientation);
            // exif.setAttribute(ExifInterface.ORIENTATION_ROTATE_90, 90);
            Log.e("orientation", "" + orientation);
            Matrix m = new Matrix();
            if ((orientation == ExifInterface.ORIENTATION_ROTATE_180)) {
                m.postRotate(180);
                // m.postScale((float) bm.getWidth(), (float) bm.getHeight());
                // if(m.preRotate(90)){
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                        bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                m.postRotate(90);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);
                return bitmap;
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                m.postRotate(270);
                Log.e("in orientation", "" + orientation);
                bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),bm.getHeight(), m, true);
                return bitmap;
            }
            return bitmap;
        } catch (Exception e) {
            return null;
        }
    }


    public static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

   /* public static int getTabsHeight(Context context) {
        return (int) context.getResources().getDimension(R.dimen.tabsHeight);
    }*/


    public static String getTimeString(long time){
        String timeStr = "N/A";
        try {
            Calendar cal = Calendar.getInstance();
            cal.setTimeZone(TimeZone.getTimeZone("UTC"));
            cal.setTimeInMillis(time);
            return (cal.get(Calendar.DAY_OF_MONTH) + "/"
                    + (cal.get(Calendar.MONTH) + 1) + "/"
                    + cal.get(Calendar.YEAR) + " "
                    + cal.get(Calendar.HOUR_OF_DAY) + ":"
                    + cal.get(Calendar.MINUTE));
            //+ ":"
            // + cal.get(Calendar.SECOND));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeStr;
    }

    public static Bitmap getMarkerIcon(final Context context,int drawable,String location) {
        View cluster = LayoutInflater.from(context).inflate(R.layout.marker_icon,null);
        TextView tv_marker_text = (TextView) cluster.findViewById(R.id.tv_marker_text);
        tv_marker_text.setText(context.getString(R.string.click_here_to_check_feasibility));
        TextView tv_location = (TextView) cluster.findViewById(R.id.tv_location);
        tv_location.setVisibility(View.VISIBLE);
        tv_location.setText(location);
        ImageView iv_marker = (ImageView) cluster.findViewById(R.id.iv_marker);
        iv_marker.setImageResource(drawable);
        cluster.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        cluster.layout(0, 0, cluster.getMeasuredWidth(),cluster.getMeasuredHeight());
        final Bitmap clusterBitmap = Bitmap.createBitmap(cluster.getMeasuredWidth(),cluster.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(clusterBitmap);
        cluster.draw(canvas);
        return clusterBitmap;
    }




    public static SpannableStringBuilder getActionbarTitle(String title, Typeface typeface){
        SpannableStringBuilder titleSpan = new SpannableStringBuilder(title);
        titleSpan.setSpan (new CustomTypefaceSpan("", typeface), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        return titleSpan;
    }
    public static String base64Encode(String string) {
        try {
            byte[] data = string.getBytes("UTF-8");
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }
    public static String base64Decode(String string){
        byte[] data = Base64.decode(string, Base64.DEFAULT);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return string;
        }
    }
    /*
    public static String getMac(Context ctx){
        WifiManager manager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        return info.getMacAddress();
    }*/

    private static String getIMEI(Context context){
        String identifier = null;
        TelephonyManager tm = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null)
            identifier = tm.getDeviceId();
        if (identifier == null || identifier .length() == 0)
            identifier = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return identifier;
    }


    public static void logOut(Activity ctx){
        SharePrefUtil.clearSharePref(ctx);
        Intent mIntent = new Intent(ctx, LoginActivity.class);
        mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(mIntent);
        ctx.finish();
    }
}