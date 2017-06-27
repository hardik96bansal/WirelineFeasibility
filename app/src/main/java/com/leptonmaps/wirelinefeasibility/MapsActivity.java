package com.leptonmaps.wirelinefeasibility;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.leptonmaps.wirelinefeasibility.adapters.DpListAdapter;
import com.leptonmaps.wirelinefeasibility.adapters.NavAdapter;
import com.leptonmaps.wirelinefeasibility.adapters.TypeSpinnerAdapter;
import com.leptonmaps.wirelinefeasibility.appnetwork.AsyncThread;
import com.leptonmaps.wirelinefeasibility.appnetwork.ReqRespBean;
import com.leptonmaps.wirelinefeasibility.appnetwork.WEBAPI;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.apputil.SharePrefUtil;
import com.leptonmaps.wirelinefeasibility.constants.AppConstants;
import com.leptonmaps.wirelinefeasibility.datamodels.NavBean;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import com.leptonmaps.wirelinefeasibility.fragments.TouchableMapFragment;
import com.leptonmaps.wirelinefeasibility.interfaces.BaseInterface;
import com.leptonmaps.wirelinefeasibility.jsonparser.JsonParser;
import com.leptonmaps.wirelinefeasibility.request.CircleRequest;
import com.leptonmaps.wirelinefeasibility.request.CostRequest;
import com.leptonmaps.wirelinefeasibility.request.DirectionRequest;
import com.leptonmaps.wirelinefeasibility.request.TowerRequest;
import com.leptonmaps.wirelinefeasibility.response.CostResponse;
import com.leptonmaps.wirelinefeasibility.response.CountResponse;
import com.leptonmaps.wirelinefeasibility.response.DPResponse;
import com.leptonmaps.wirelinefeasibility.response.DirectionLegResponse;
import com.leptonmaps.wirelinefeasibility.response.ServiceTypeResponse;
import com.leptonmaps.wirelinefeasibility.response.TowerResponse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


public class MapsActivity extends BaseActivity implements BaseInterface, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private static final String TAG = MapsActivity.class.getSimpleName();
    private static final String TAGDP="dp";
    private static final String TAGMYLOCATION="mylocation";

    protected static final double DEFAULT_LAT = 12;
    protected static final int REQUEST_CODE_GOOGLE_PLAY_SERVICES = 9004;
    protected static final int REQUEST_CODE_PLAY_SERVICES_RESOLUTION = 901;
    protected static final int REQUEST_CODE_AUTOCOMPLETE = 902;
    protected static final int REQUEST_CODE_CURRENT_LOCATION = 903;
    private final int PLAY_SERVICE_ALERT = 4;
    private static final int ACCESS_FINE_LOCATION = 5;
    public static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    public static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2;
    public final int COLOR_POLYLINE=Color.BLUE;
    public final int COLOR_DOTTED_POLYLINE=Color.DKGRAY;
    public final int COLOR_AERIAL_POLYLINE=Color.GREEN;
    public static String finalurl;

    public static final int PATTERN_GAP_LENGTH_PX = 10;
    public static final PatternItem DOT = new Dot();
    public static final PatternItem GAP = new Gap(PATTERN_GAP_LENGTH_PX);
    public static final List<PatternItem> PATTERN_DOTTED = Arrays.asList(GAP, DOT);

    private GoogleApiClient googleApiClient;
    private GoogleMap mMap;
    private TouchableMapFragment mapFragment = null;
    private Marker searchMarker = null;
    private LatLng destinationMarker=null,startMarker=null;
    private TextView tv_current_loc, tv_tower,tv_required_speed,tv_service_type,tv_circle,
            tv_click_to_check,tv_segment,tv_cww_loc,tv_dc_loc,tv_location_end,tv_sat_view,tv_wire_type,
            tv_element_id,tv_elem_id,tv_record_id,tv_rec_id,tv_dpchooser_heading,tv_popup_true_feasible,tv_popup_true_info,tv_popup_true_cross1,tv_popup_true_notfeasible,tv_popup_true_retry,tv_popup_true_cross2;
    private final float zoomLevel = 17.0f;
    protected LocationRequest mLocationRequest;
    private boolean isCurrentLoc = false;
    private  AsyncThread mAsyncThread = null;
    private Button btn_feasibility,btn_cancel;
    private LinearLayout ll_service_layout,ll_dp_mini_info,ll_dp_chooser,ll_feasibility_popup,ll_feasible_true,ll_feasible_false;
    private Spinner sp_service_type,sp_speed_type,sp_circle_type,sp_segment_type,sp_cww_loc,sp_dc_loc,sp_location_end,sp_wire_type,sp_speed;
    //private EditText et_speed;
    private RelativeLayout rl_map_container;
    private HashMap<String,DPResponse.Result> markerMap = new HashMap<>();
    private ArrayList<Marker> towerMarkerList = new ArrayList<>();
    private ArrayList<String> services = null;
    private ArrayList<String> circles = null;
    private ArrayList<DPResponse.Result> mDpResponseResults=null;
    private ImageView iv_tower,lepton_logo;
    private DpListAdapter dpListAdapter=null;
    private RequestQueue requestQueue;
    private Polyline polyline,dottedPolyline1=null,dottedPolyline2=null;
    private ListView lv_dpList;
    private ArrayList<Polyline> polylines;
    private ArrayList<LatLng> points,points1,points2;

    private double lineAngle_toEndPoint=0,lineAngle_EndPoint_toElem=0;
    private boolean isFeasible=true;

    private ProgressDialog showDpProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        markerMap.clear();
        towerMarkerList.clear();
        initViews();
        setTypeface();
        setListener();
        setTag();
        if (checkPlayServices()) {
            buildGoogleApiClient();
        } else {
            AppUtil.errorDialog(this, getString(R.string.error), getString(R.string.play_service_not_available), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    MapsActivity.this.finish();
                }
            });
        }
        String[] speedUnitArray = getResources().getStringArray(R.array.speed_type);
        List speedUnitList = Arrays.asList(speedUnitArray);
        sp_speed_type.setAdapter(new TypeSpinnerAdapter(this,speedUnitList));
        Log.d("check1","onCreate");
        summaryCountRequest();
        requestQueue= Volley.newRequestQueue(this);
        clickMarkerBtnState(false);
        polylines=new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        // searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            openAutocompleteActivity();
            showHideServiceLayout(false);
            showDpMiniInfoLayout(false);
            if(ll_feasibility_popup!=null)showFeasiblePopup(false);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void showDpMiniInfoLayout(boolean isShow){
        if(isShow){
            ll_dp_mini_info.setVisibility(View.VISIBLE);
            //Log.e("yolo height",""+ll_dp_mini_info.getHeight());
            resizeMapFragmentFull(false);

        }else{
            ll_dp_mini_info.setVisibility(View.GONE);
            resizeMapFragmentFull(true);

        }
    }

    private void showFeasiblePopup(boolean isShow){
        if(isShow){
            if(ll_feasibility_popup!=null)ll_feasibility_popup.setVisibility(View.VISIBLE);
            //Log.e("yololololololo height",""+ll_feasibility_popup.getHeight());
            //resizeMapFragmentFull(false);

        }else{
            ll_feasibility_popup.setVisibility(View.GONE);
            resizeMapFragmentFull(true);

        }
    }

    private void showHideServiceLayout(boolean isShow){
        if(isShow){
            ll_service_layout.setVisibility(View.VISIBLE);
        }else{
            ll_service_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void initViews() {
        View view = mLayoutInflater.inflate(R.layout.activity_maps,null);
        View view1=mLayoutInflater.inflate(R.layout.list_item_dpinfo,null);
        View view2=mLayoutInflater.inflate(R.layout.dp_chooser,null);

        ll_view_container.addView(view);
        mapFragment = (TouchableMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tv_current_loc = (TextView)findViewById(R.id.tv_current_loc);
        tv_tower = (TextView)findViewById(R.id.tv_tower);
        tv_sat_view=(TextView)findViewById(R.id.tv_sat_view);

        tv_required_speed = (TextView)findViewById(R.id.tv_required_speed);
        tv_wire_type = (TextView)findViewById(R.id.tv_wire_type);

        tv_element_id =(TextView)view1.findViewById(R.id.tv_element_id);
        tv_elem_id=(TextView)view1.findViewById(R.id.tv_elem_id);
        tv_rec_id=(TextView)view1.findViewById(R.id.tv_rec_id);
        tv_record_id=(TextView)view1.findViewById(R.id.tv_record_id);

        tv_popup_true_feasible=(TextView)findViewById(R.id.tv_popup_feasible);
        tv_popup_true_info=(TextView)findViewById(R.id.tv_popup_info);
        tv_popup_true_cross1=(TextView)findViewById(R.id.tv_popup_cross1);

        tv_popup_true_notfeasible=(TextView)findViewById(R.id.tv_popup_notfeasible);
        tv_popup_true_retry=(TextView)findViewById(R.id.tv_popup_retry);
        tv_popup_true_cross2=(TextView)findViewById(R.id.tv_popup_cross2);


        tv_dpchooser_heading=(TextView)view2.findViewById(R.id.tv_miniinfo_heading);
        ll_dp_chooser=(LinearLayout)view2.findViewById(R.id.ll_dp_chooser);

        ll_feasible_true=(LinearLayout)findViewById(R.id.ll_popup_feasible_true);
        ll_feasible_false=(LinearLayout)findViewById(R.id.ll_popup_feasible_false);
        ll_feasibility_popup=null;

        /*
        tv_service_type = (TextView)findViewById(R.id.tv_service_type);
        tv_circle = (TextView)findViewById(R.id.tv_circle);
        tv_segment = (TextView)findViewById(R.id.tv_segment);

        tv_cww_loc = (TextView)findViewById(R.id.tv_cww_loc);
        tv_dc_loc = (TextView)findViewById(R.id.tv_dc_loc);
        tv_location_end = (TextView)findViewById(R.id.tv_location_end);
        */
        tv_click_to_check = (TextView)findViewById(R.id.tv_click_to_check);

        iv_tower = (ImageView)findViewById(R.id.iv_tower);
        btn_feasibility = (Button)findViewById(R.id.btn_feasibility);
        btn_cancel = (Button)findViewById(R.id.btn_cancel);
        ll_service_layout = (LinearLayout)findViewById(R.id.ll_service_layout);
        ll_dp_mini_info=(LinearLayout)findViewById(R.id.ll_dpminiinfo_layout);

        lv_dpList=(ListView)findViewById(R.id.lv_dp_miniinfo);
        lepton_logo=(ImageView)findViewById(R.id.lepton_logo);

        sp_speed_type = (Spinner)findViewById(R.id.sp_speed_type);
        sp_wire_type=(Spinner)findViewById(R.id.sp_wire_type);
        sp_speed=(Spinner)findViewById(R.id.sp_speed);

        /*
        sp_service_type = (Spinner)findViewById(R.id.sp_service_type);
        sp_segment_type = (Spinner)findViewById(R.id.sp_segment_type);
        sp_circle_type = (Spinner)findViewById(R.id.sp_circle_type);
        sp_cww_loc = (Spinner)findViewById(R.id.sp_cww_loc);
        sp_dc_loc = (Spinner)findViewById(R.id.sp_dc_loc);
        sp_location_end = (Spinner)findViewById(R.id.sp_location_end);
        */


        //et_speed = (EditText)findViewById(R.id.et_speed);
        rl_map_container = (RelativeLayout)findViewById(R.id.rl_map_container);
    }
    private void setTypeface() {
        tv_tower.setTypeface(icoMoon);
        tv_sat_view.setTypeface(icoMoon);
        tv_current_loc.setTypeface(icoMoon);
        //et_speed.setTypeface(vodafoneRgBd);
        tv_required_speed.setTypeface(vodafoneRgBd);
        tv_wire_type.setTypeface(vodafoneRgBd);
        /*
        tv_segment.setTypeface(vodafoneRgBd);
        tv_service_type.setTypeface(vodafoneRgBd);
        tv_circle.setTypeface(vodafoneRgBd);
        */
        btn_cancel.setTypeface(vodafoneRgBd);
        btn_feasibility.setTypeface(vodafoneRgBd);

        tv_element_id.setTypeface(vodafoneRgBd);
        tv_elem_id.setTypeface(vodafoneRgBd);
        tv_rec_id.setTypeface(vodafoneRgBd);
        tv_record_id.setTypeface(vodafoneRgBd);
        tv_dpchooser_heading.setTypeface(vodafoneRgBd);

        tv_popup_true_cross1.setTypeface(icoMoon);
        tv_popup_true_info.setTypeface(vodafoneRg);
        tv_popup_true_feasible.setTypeface(vodafoneRgBd);

        tv_popup_true_cross2.setTypeface(icoMoon);
        tv_popup_true_retry.setTypeface(vodafoneRg);
        tv_popup_true_notfeasible.setTypeface(vodafoneRgBd);

        tv_click_to_check.setTypeface(vodafoneRgBd);
        /*
        tv_cww_loc.setTypeface(vodafoneRgBd);
        tv_dc_loc.setTypeface(vodafoneRgBd);
        tv_location_end.setTypeface(vodafoneRgBd);
        */

    }


    //@Override
    public void setListener() {
        tv_current_loc.setOnClickListener(mOnClickListener);
        tv_tower.setOnClickListener(mOnClickListener);
        btn_feasibility.setOnClickListener(mOnClickListener);
        btn_cancel.setOnClickListener(mOnClickListener);
        iv_tower.setOnClickListener(mOnClickListener);
        tv_click_to_check.setOnClickListener(mOnClickListener);
        tv_sat_view.setOnClickListener(mOnClickListener);
        tv_popup_true_cross1.setOnClickListener(mOnClickListener);
        tv_popup_true_cross2.setOnClickListener(mOnClickListener);
        tv_popup_true_retry.setOnClickListener(mOnClickListener);
        tv_popup_true_info.setOnClickListener(mOnClickListener);
        lv_dpList.setOnItemClickListener(mOnItemClickListener);
    }
    private void setTag(){
        tv_tower.setTag(false);
        tv_sat_view.setTag(false);
        //tv_tower.setTextColor(getResources().getColor(R.color.grey_1));
        iv_tower.setImageResource(R.drawable.tower_grey);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("check1","onMapReady");
        mMap = googleMap;
        destinationMarker=new LatLng(DEFAULT_LAT, 77.219531);
        if(mMap==null){

        }
        mMap.setOnCameraChangeListener(mOnCameraChangeListener);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setOnMarkerClickListener(markerClickListener);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);

            return;
        }else{
            mMap.setMyLocationEnabled(true);
            //destinationMarker.setPosition(new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude));
            //destinationMarker=new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
            showMyLocation();
        }
    }
    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, REQUEST_CODE_PLAY_SERVICES_RESOLUTION).show();
            } else {
                AppUtil.errorDialog(this, getString(R.string.error), getString(R.string.play_service_not_available), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MapsActivity.this.finish();
                    }
                });
            }
            return false;
        }
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        processGPSTurnOnProcess();
        startLocationUpdates();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            return;
        }else{
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (googleApiClient != null) googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        int connectionStatus = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (connectionStatus == ConnectionResult.SUCCESS) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
                return;
            }
        } else {
            Log.e(TAG, GooglePlayServicesUtil.getErrorString(connectionStatus));
            GooglePlayServicesUtil.getErrorDialog(connectionStatus, this, REQUEST_CODE_GOOGLE_PLAY_SERVICES).show();
        }

       /* if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION1) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION1}, MY_LOCATION_PERMISSION);
            return;
        }*/


    }


    private void buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this).addApi(LocationServices.API).addConnectionCallbacks(MapsActivity.this).addOnConnectionFailedListener(this).build();
            googleApiClient.connect();
        } else {
            if (googleApiClient.isConnected()) {
                processGPSTurnOnProcess();
            } else {
                googleApiClient.connect();
            }
        }
    }
    private void processGPSTurnOnProcess() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(30 * 1000);
        locationRequest.setFastestInterval(5 * 1000);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true); // this is the key ingredient
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        //TODO:
                        startLocationUpdates();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            status.startResolutionForResult(MapsActivity.this, LocationSettingsStatusCodes.RESOLUTION_REQUIRED);
                        } catch (IntentSender.SendIntentException e) {
                            //showToast("RESOLUTION_REQUIRED:"+e);
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        AppUtil.errorDialog(MapsActivity.this, getString(R.string.error), getString(R.string.gps_not_enable), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MapsActivity.this.finish();
                            }
                        });
                        break;
                }
            }
        });
    }

    protected void startLocationUpdates() {
        if (googleApiClient == null || !googleApiClient.isConnected()) return;
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapsActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, mLocationListener);
    }

    public void openAutocompleteActivity() {
        try {
            Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(MapsActivity.this);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivityForResult(intent, REQUEST_CODE_AUTOCOMPLETE);
        } catch (GooglePlayServicesRepairableException e) {
            GoogleApiAvailability.getInstance().getErrorDialog(MapsActivity.this, e.getConnectionStatusCode(), PLAY_SERVICE_ALERT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            String message = getString(R.string.play_service_not_available) + GoogleApiAvailability.getInstance().getErrorString(e.errorCode);
            Log.e(TAG, message);
        }
    }

    private void moveCamera(double lat, double lng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), zoomLevel));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);

        //isPolygonRequestRunning = false;
        if (requestCode == REQUEST_CODE_AUTOCOMPLETE) {
            if (resultCode == MapsActivity.this.RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(MapsActivity.this, data);
                place.getAddress();
                if (searchMarker != null) searchMarker.remove();
                searchMarker = showMarker(R.drawable.marker_red,place.getLatLng().latitude, place.getLatLng().longitude);
                destinationMarker=new LatLng(searchMarker.getPosition().latitude,searchMarker.getPosition().longitude);
                moveCamera(place.getLatLng().latitude, place.getLatLng().longitude);
                updateLocation(place.getLatLng().latitude, place.getLatLng().longitude);
                clickMarkerBtnState(false);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(MapsActivity.this, data);
                showToast(status.getStatusMessage());
                Log.e(TAG, "Error: Status = " + status.toString());
            } else if (resultCode == MapsActivity.this.RESULT_CANCELED) {
                // Indicates that the activity closed before a selection was made. For example if
                // the user pressed th e back button.
            } else if (requestCode == REQUEST_CODE_CURRENT_LOCATION) {
                // reloadMap();
            }
        }/*else if(requestCode ==INTENT_CHECK_FEASIBILITY_ACT){
            if (resultCode == MapsActivity.this.RESULT_OK) {
                tv_tower.setTag(false);
                iv_tower.setImageResource(R.drawable.tower_grey);
                //tv_tower.setTextColor(getResources().getColor(R.color.grey_1));
                removeTowers();
                //TODO: show feasibility result data
                flag = data.getIntExtra("flag",-1);
                if(data.getSerializableExtra("obj") != null) {
                    feasibleResultData = (ArrayList<CostResponse.Result>) data.getSerializableExtra("obj");
                    if(feasibleResultData != null){
                        showFeasibleTowers(feasibleResultData);
                        if(feasibleResultData.size() == 1 && feasibleResultData.get(0) != null){
                            drawLine(feasibleResultData.get(0).getLatitude(),feasibleResultData.get(0).getLongitude());
                        }
                        clickMarkerBtnState(true);
                    }
                }else{
                    showToast(getString(R.string.invalid_data_passed));
                }

            }else{
                //TODO:
            }
        }*/
    }

    private Marker showMarker(int drawable,double lat,double lng){
        Marker  marker = null;
        MarkerOptions opt = new MarkerOptions();
        opt.position(new LatLng(lat,lng));
        String location = "Location: "+lat + "," +lng;
        opt.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_red));
        //  opt.icon(BitmapDescriptorFactory.fromBitmap(AppUtil.getMarkerIcon(this,drawable,location)));
        marker = mMap.addMarker(opt);
        marker.setZIndex(1f);
        return  marker;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
                        return;
                    }
                    if (mMap != null) mMap.setMyLocationEnabled(true);
                    //TODO: turn on gps
                    buildGoogleApiClient();
                } else {
                    //TODO:
                }
                break;
        }
    }
    private void showMyLocation() {
        Log.d("check1","showMyLocation");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, ACCESS_FINE_LOCATION);
            return;
        }
        if (mMap != null && mMap.getMyLocation() != null) { // Check to ensure coordinates aren't null, probably a better way of doing this...
            if(searchMarker != null)searchMarker.remove();
            searchMarker = showMarker(R.drawable.marker_red,mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());
            searchMarker.setTag(TAGMYLOCATION);
            destinationMarker=new LatLng(searchMarker.getPosition().latitude,searchMarker.getPosition().longitude);
            moveCamera(mMap.getMyLocation().getLatitude(),mMap.getMyLocation().getLongitude());

            updateLocation(searchMarker.getPosition().latitude,searchMarker.getPosition().longitude);
        }
    }




    private void towerRequest(double lat, double lng){
        TowerRequest mTowerRequest = new TowerRequest();
        mTowerRequest.setCust_latitude(lat);
        mTowerRequest.setCust_longitude(lng);
        mTowerRequest.setToken(SharePrefUtil.getToken(this));
        JSONObject json = JsonParser.getBeanToJson(mTowerRequest);
        ReqRespBean mBean = new ReqRespBean();
        if(json != null)mBean.setJson(json.toString());
        HashMap<String,String> headers = new HashMap<>(1);
        headers.put("Authorization","bearer "+SharePrefUtil.getToken(this));
        mBean.setHeader(headers);
        mBean.setApiType(APIType.GET_TOWERS);
        mBean.setRequestmethod(WEBAPI.POST);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_TOWERS));
        mBean.setCtx(this);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
    }

    private void dpRequest(double lat,double lng){

        /*

        DPRequest mDPRequest=new DPRequest();
        mDPRequest.setCust_latitude(lat);
        mDPRequest.setCust_latitude(lng);
        mDPRequest.setToken(SharePrefUtil.getToken(this));
        mDPRequest.setRadius(300);
        JSONObject json=JsonParser.getBeanToJson(mDPRequest);
        ReqRespBean mBean=new ReqRespBean();

        if(json !=null)mBean.setJson(json.toString());
        HashMap<String,String> headers = new HashMap<>(1);
        headers.put("Authorization","bearer "+SharePrefUtil.getToken(this));
        mBean.setHeader(headers);
        mBean.setApiType(APIType.GET_DP);
        mBean.setRequestmethod(WEBAPI.GET);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_TOWERS));
        mBean.setCtx(this);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
        */

        if(showDpProgressDialog != null)showDpProgressDialog.dismiss();

        String url=AppConstants.BASE_URL;
        StringBuilder sb=new StringBuilder(url);
        sb.append("api/Network/Entities/?");
        sb.append("Radius=300");
        sb.append("&Latitude="+lat);
        sb.append("&Longitude="+lng);
        sb.append("&ElementTypes=DP");

        url=sb.toString();
        //showToast(url);
        Log.v("TAG1",url);
        finalurl=url;



        class TempRequest extends AsyncTask<String,Integer,String>{
            @Override
            protected void onPreExecute() {
                //showDpProgressDialog.show();

                try {
                    if(showDpProgressDialog != null)
                        showDpProgressDialog.show();
                    Log.e("showdpprogdialog shown",showDpProgressDialog.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                super.onPreExecute();
            }
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            public void initProgressDialog(Context context, DialogInterface.OnCancelListener mOnCancelListener){
                showDpProgressDialog = new ProgressDialog(context, R.style.progressDialogTheme);
                showDpProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                showDpProgressDialog.setCancelable(true);
                showDpProgressDialog.setOnCancelListener(mOnCancelListener);
                //mProgressDialog.setMessage("Please wait...");
                //ProgressBar spinner = new android.widget.ProgressBar(context,null,android.R.attr.progressBarStyle);
                //spinner.getIndeterminateDrawable().setColorFilter(0xFFFF0000, android.graphics.PorterDuff.Mode.MULTIPLY);
            }

            @Override
            protected String doInBackground(String... strings) {

                JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, finalurl, null,
                        new Response.Listener<JSONArray>()
                        {
                            @Override
                            public void onResponse(JSONArray response) {
                                // display response
                                Log.d("Response", response.toString());
                                JSONObject jo=new JSONObject();
                                try {
                                    jo.put("results",response);
                                } catch (JSONException e) {
                                    Log.e("JSON Error",e.toString());
                                    e.printStackTrace();
                                }
                                ReqRespBean result=new ReqRespBean();
                                result.setmHandler(mHandler);
                                result.setApiType(APIType.GET_DP);
                                result.setJson(jo.toString());
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

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    if(showDpProgressDialog != null) {
                        //mProgressDialog.dismiss();
                        //showDpProgressDialog = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

        TempRequest tempRequest=new TempRequest();
        tempRequest.initProgressDialog(MapsActivity.this,mOnCancelListener);
        tempRequest.execute();
    }


    private void serviceTypeRequest(){
        ReqRespBean mBean = new ReqRespBean();
        mBean.setApiType(APIType.GET_SERVICE_TYPE);
        mBean.setRequestmethod(WEBAPI.GET);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_SERVICE_TYPE));
        mBean.setCtx(this);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
    }
    private void circleTypeRequest(){
        CircleRequest mCircleRequest = new CircleRequest(AppConstants.LAT,AppConstants.LNG);
        JSONObject json = JsonParser.getBeanToJson(mCircleRequest);
        ReqRespBean mBean = new ReqRespBean();
        mBean.setJson(json.toString());
        mBean.setApiType(APIType.GET_CIRCLE_TYPE);
        mBean.setRequestmethod(WEBAPI.POST);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_CIRCLE_TYPE));
        mBean.setCtx(this);
        HashMap<String,String> headers = new HashMap<>(1);
        headers.put("Authorization","bearer "+SharePrefUtil.getToken(this));
        mBean.setHeader(headers);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
    }

    private void costRequest(){
        CostRequest mCostRequest = new CostRequest();
        mCostRequest.setCust_latitude(AppConstants.LAT);
        mCostRequest.setCust_longitude(AppConstants.LNG);
        //TODO:
        mCostRequest.setService_type(sp_service_type.getSelectedItem().toString());
        mCostRequest.setBandwidth(Integer.valueOf(sp_speed.getSelectedItem().toString()));
        mCostRequest.setBandwidth_unit(sp_speed_type.getSelectedItem().toString());
        mCostRequest.setCircle(sp_circle_type.getSelectedItem().toString());

        mCostRequest.setBusiness_segment(sp_segment_type.getSelectedItem().toString());
        mCostRequest.setCww_location(sp_cww_loc.getSelectedItem().toString());
        mCostRequest.setDc_location(sp_dc_loc.getSelectedItem().toString());
        mCostRequest.setLocation_end(sp_location_end.getSelectedItem().toString());

        JSONObject json = JsonParser.getBeanToJson(mCostRequest);
        ReqRespBean mBean = new ReqRespBean();
        if(json != null)mBean.setJson(json.toString());
        HashMap<String,String> headers = new HashMap<>(1);
        headers.put("Authorization","bearer "+SharePrefUtil.getToken(this));
        mBean.setHeader(headers);
        mBean.setApiType(APIType.GET_COST);
        mBean.setRequestmethod(WEBAPI.POST);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_COST));
        mBean.setCtx(this);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
    }

    private void summaryCountRequest(){

        /*
        Log.d("check1","summaryCountRequest called");
        SummaryRequest summaryRequest = new SummaryRequest();
        summaryRequest.setUsername(SharePrefUtil.getUserId(this));
        summaryRequest.setFilter_type(ActivitySummary.TODAY);
        JSONObject json = JsonParser.getBeanToJson(summaryRequest);
        ReqRespBean mBean = new ReqRespBean();
        mBean.setJson(json.toString());
        mBean.setApiType(APIType.GET_SUMMARY_COUNT);
        mBean.setRequestmethod(WEBAPI.POST);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.GET_SUMMARY_COUNT));
        mBean.setCtx(this);
        HashMap<String,String> headers = new HashMap<>(1);
        headers.put("Authorization","bearer "+SharePrefUtil.getToken(this));
        mBean.setHeader(headers);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);

        */
    }

    private void getPath(double fromLat,double fromLng,double toLat,double toLng){
        //removePolylines();
        DirectionRequest directionRequest=new DirectionRequest(MapsActivity.this,fromLat,fromLng,toLat,toLng,mOnCancelListener,mHandler);
        directionRequest.getResponse();
        if(directionRequest!=null)removePolylines();
        showDPs(mDpResponseResults);
        updateLatLngBounds(fromLat,fromLng,toLat,toLng);
    }

    private void reverseGeoCode(){
        ReqRespBean mBean = new ReqRespBean();
        mBean.setApiType(APIType.REVERSE_GEO_CODE);
        mBean.setRequestmethod(WEBAPI.GET);
        StringBuilder stringBuilder = new StringBuilder("http://maps.googleapis.com/maps/api/geocode/json");
        stringBuilder.append("?latlng=");
        stringBuilder.append(AppConstants.LAT);
        stringBuilder.append(",");
        stringBuilder.append(AppConstants.LNG);
        stringBuilder.append("&sensor=true");
        mBean.setUrl(stringBuilder.toString());
        mBean.setCtx(this);
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(this,mOnCancelListener);
        mAsyncThread.execute(mBean);
    }
    DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            if(mAsyncThread != null)mAsyncThread.cancel(true);
            mAsyncThread = null;
        }
    };

    private void drawPath(ArrayList<LatLng> points){

        PolylineOptions polylineOptions=new PolylineOptions();
        //Log.e("Polyline status","Main polyline has "+points.size());

        polylineOptions.addAll(points);
        polylineOptions.width(9);
        polylineOptions.color(COLOR_POLYLINE);


        if(polylineOptions!=null){
            polyline=mMap.addPolyline(polylineOptions);
            polyline.setTag("Poly yolo");
            //Log.e("Polyline Status","Main polyline Drawn");
            //return polyline;
        }

        //return null;
    }

    private Polyline drawDottedPath(ArrayList<LatLng> points){
        PolylineOptions polylineOptions=new PolylineOptions();

        polylineOptions.addAll(points);
        //Log.e("Polyline status","Dotted polyline has "+points.size());
        polylineOptions.width(11);
        polylineOptions.color(COLOR_DOTTED_POLYLINE);
        polylineOptions.pattern(PATTERN_DOTTED);



        if(polylineOptions!=null){
            polyline=mMap.addPolyline(polylineOptions);
            return polyline;
        }

        return null;

    }

    private Polyline drawAerialPath(ArrayList<LatLng> points){
        PolylineOptions polylineOptions=new PolylineOptions();

        polylineOptions.addAll(points);
        //Log.e("Polyline status","Dotted polyline has "+points.size());
        polylineOptions.width(11);
        polylineOptions.color(COLOR_AERIAL_POLYLINE);
        polylineOptions.pattern(PATTERN_DOTTED);



        if(polylineOptions!=null){
            polyline=mMap.addPolyline(polylineOptions);
            return polyline;
        }

        return null;

    }


    private double getAngleInDegrees(double latA,double latB,double lngA,double lngB) {
        double deltaX = latB - latA;
        double deltaY = lngB - lngA;
        double rad = Math.atan2(deltaY, deltaX);
        double deg = rad * (180 / Math.PI);
        if (deg < 0)
            deg += 360;
        return deg;
    }



    private boolean IsLeftSidePointUsingAngles(double ha,double ca) {
        double ra = ha - 180;
        boolean isLeft = (ca >= ra && ca <= ha);
        return isLeft;
    }

    private boolean getCrossings(boolean checkForRight) {
        boolean isFeas = false;
        // Validate right turn roads crossing feasibility
        if (fnIsDPChecked()) {
            //if ($("#directions-panel td:Contains('Turn right')").length == 0 && $("#directions-panel td:Contains('Destination will be on the right')").length == 0) {
            //Log.e("lineAngle_toEndPoint",lineAngle_toEndPoint+"");
            boolean isLeftSidePoint = IsLeftSidePointUsingAngles(lineAngle_toEndPoint, lineAngle_EndPoint_toElem);
            Log.e("isNextPointOnLeft",""+isLeftSidePoint);
            Log.e("isFirstTurnRight",""+checkForRight);
            if (isLeftSidePoint && checkForRight==false) {
                //$(_tdObj).addClass('feas');

                Log.e("Turn Taken","left");
                isFeas = true;
            }
            else if (isLeftSidePoint==false && checkForRight) {
                //$(_tdObj).addClass('feas');

                isFeas = true;
                Log.e("Turn Taken","right");
            }

            /*
            else if (isLeftSidePoint && checkForRight==true) {
                //$(_tdObj).addClass('feas');

                Log.e("Turn Taken","left");
                isFeas = false;
            }
            else if (isLeftSidePoint==false && checkForRight==false) {
                //$(_tdObj).addClass('feas');

                isFeas = false;
                Log.e("Turn Taken","right");
            }
            else isFeas=false;*/
            //}
            //isFeas ? displayMessage('Feasible', 'green') : displayMessage('Not Feasible', 'red');
        }
        return isFeas;
    }

    private boolean checkFirstTurn() {
            boolean isLeftSidePoint = IsLeftSidePointUsingAngles(lineAngle_toEndPoint, lineAngle_EndPoint_toElem);
        return !isLeftSidePoint;
    }




    private boolean fnIsDPChecked() {
        return true;
    }

    private List decodePoly(String encoded) {

        List poly = new ArrayList();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }

        return poly;
    }

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mAsyncThread = null;
            if (msg.obj == null) {
                return false;
            } else {
                ReqRespBean mBean = (ReqRespBean) msg.obj;
                switch (mBean.getApiType()) {
                    case GET_TOWERS:
                        showHideServiceLayout(false);
                        TowerResponse mTowerResponse = (TowerResponse) JsonParser.getJsonToBean(APIType.GET_TOWERS, mBean.getJson());
                        if (mTowerResponse != null) {
                            if(mTowerResponse.getStatus() != null && mTowerResponse.getStatus().equalsIgnoreCase("OK")){
                                showTowers(mTowerResponse.getResults());
                            }else if(mTowerResponse.getStatus() != null && mTowerResponse.getStatus().equalsIgnoreCase("REQUEST_DENIED")
                                    ||mTowerResponse.getStatus() != null && mTowerResponse.getStatus().equalsIgnoreCase("SESSION_EXPIRED") ){
                                String errorMsg = mTowerResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mTowerResponse.getError_message();
                                //TODO: Logout from here
                                AppUtil.logOut(MapsActivity.this);
                                showToast(errorMsg);
                            }else{
                                String errorMsg = mTowerResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mTowerResponse.getError_message();
                                showToast(errorMsg);
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                        }

                        break;

                    case GET_DP:

                        //showDpMiniInfoLayout(false);


                        DPResponse mDpResponse = (DPResponse) JsonParser.getJsonToBean(APIType.GET_DP,mBean.getJson());
                        if(mDpResponse != null){
                            //Log.e("prog","111111111111");
                            if(mDpResponse.getResults()!=null){
                               // Log.e("prog","222222222222");
                                mDpResponseResults=mDpResponse.getResults();
                                //Log.e("prog","3333333333333");
                                dpListAdapter=new DpListAdapter(MapsActivity.this,mDpResponseResults);
                                //Log.e("prog","44444444444444");
                                lv_dpList.setAdapter(dpListAdapter);
                                //Log.e("prog","55555555555555");
                                if((boolean)tv_tower.getTag()){
                                    //Log.e("prog","666666666666");
                                    showDPs(mDpResponse.getResults());
                                }

                            }
                        }
                        break;

                    case GET_DIRECTIONS:

                        isFeasible=true;

                        points=new ArrayList<>();
                        points1=new ArrayList<>();
                        points2=new ArrayList<>();
                        points.clear();
                        points1.clear();
                        points2.clear();
                        Log.e("mapsActivity Handler",mBean.getJson());
                        List list=null;
                        DirectionLegResponse directionLegResponse=(DirectionLegResponse)JsonParser.getJsonToBean(APIType.GET_DIRECTIONS,mBean.getJson());
                        DirectionLegResponse.Distance distance=directionLegResponse.getDistance();
                        String dist=distance.getText();

                        if(directionLegResponse.getSteps()!=null){
                            ArrayList<DirectionLegResponse.Steps> steps= directionLegResponse.getSteps();
                            for (DirectionLegResponse.Steps tmp : steps) {
                                if (tmp != null) {
                                    DirectionLegResponse.Steps.Loc loc = tmp.getStart_location();
                                    String polyline = tmp.getPolyline().getPoints();
                                    list = decodePoly(polyline);

                                    for (int l = 0; l < list.size(); l++) {
                                        LatLng position = (LatLng) list.get(l);
                                        points.add(position);

                                    }
                                }
                            }
                            //points.clear();

                            //polyline=drawPath(points,polyline);





                            ArrayList<DirectionLegResponse.Steps> stps=directionLegResponse.getSteps();

                            LatLng firstPoint=new LatLng(stps.get(0).getStart_location().getLat(),stps.get(0).getStart_location().getLng());
                            LatLng lastPoint=new LatLng(stps.get(stps.size()-1).getEnd_location().getLat(),stps.get(stps.size()-1).getEnd_location().getLng());
                            LatLng firstEndPoint=new LatLng(stps.get(0).getEnd_location().getLat(),stps.get(0).getEnd_location().getLng());


                             //Log.e("yolo","dp color is"+dottedPolyline1.getColor());




                            lineAngle_toEndPoint = getAngleInDegrees(firstEndPoint.latitude, firstPoint.latitude, firstEndPoint.longitude, firstPoint.longitude);
                            lineAngle_EndPoint_toElem = getAngleInDegrees(firstEndPoint.latitude, startMarker.latitude, firstEndPoint.longitude, startMarker.longitude);



                            lineAngle_toEndPoint = getAngleInDegrees(startMarker.latitude, points.get(0).latitude, startMarker.longitude, points.get(0).longitude);
                            lineAngle_EndPoint_toElem = getAngleInDegrees(startMarker.latitude, points.get(1).latitude, startMarker.longitude, points.get(1).longitude);
                            boolean isFirstTurnRight=checkFirstTurn();

                            Log.e("isFirstTurnRight",""+isFirstTurnRight);



                            ArrayList<LatLng> pointsCheck=new ArrayList<>();


                            for(int xx=0;xx<points.size();xx=xx+2){
                                pointsCheck.add(points.get(xx));
                                //Log.e("position point "+points.size(),points.get(xx).latitude+","+points.get(xx).longitude);

                            }

                            pointsCheck.add(points.get(points.size()-1));

                            for(int xx=0;xx<pointsCheck.size()-2&&isFeasible==true;xx++){
                                lineAngle_toEndPoint = getAngleInDegrees(pointsCheck.get(xx).latitude, pointsCheck.get(xx+1).latitude, pointsCheck.get(xx).longitude, pointsCheck.get(xx+1).longitude);
                                lineAngle_EndPoint_toElem = getAngleInDegrees(pointsCheck.get(xx).latitude, pointsCheck.get(xx+1).latitude, pointsCheck.get(xx).longitude, pointsCheck.get(xx+2).longitude);
                                isFeasible=getCrossings(isFirstTurnRight);
                                if(isFeasible==false)break;
                                Log.e("position pointCheck "+xx,pointsCheck.get(xx).latitude+","+pointsCheck.get(xx).longitude);

                            }

                            //isFeasible=getCrossings();
                            Log.e("position pointcheck","checking for final path");
                            lineAngle_toEndPoint = getAngleInDegrees(pointsCheck.get(pointsCheck.size()-2).latitude, pointsCheck.get(pointsCheck.size()-1).latitude, pointsCheck.get(pointsCheck.size()-2).longitude, pointsCheck.get(pointsCheck.size()-1).longitude);
                            lineAngle_EndPoint_toElem = getAngleInDegrees(pointsCheck.get(pointsCheck.size()-2).latitude, destinationMarker.latitude, pointsCheck.get(pointsCheck.size()-2).longitude, destinationMarker.longitude);
                            if(isFeasible==true)isFeasible=getCrossings(isFirstTurnRight);



                            if(isFeasible){
                                drawPath(points);
                                points.clear();

                                points1.add(firstPoint);
                                points1.add(startMarker);
                                dottedPolyline1=drawDottedPath(points1);

                                points.clear();

                                points2.add(lastPoint);
                                points2.add(destinationMarker);
                                dottedPolyline2=drawDottedPath(points2);

                                points2.clear();

                                points2.add(startMarker);
                                points2.add(destinationMarker);
                                dottedPolyline2=drawAerialPath(points2);


                                polylines.add(polyline);
                                polylines.add(dottedPolyline1);
                                polylines.add(dottedPolyline2);


                            }

                            assignPopupWindow(isFeasible);


                        }
                        else{
                            showToast("Error getting details");
                        }


                        break;



                   /* case GET_SERVICE_TYPE:
                        circleTypeRequest();
                        ServiceTypeResponse mServiceTypeResponse = (ServiceTypeResponse) JsonParser.getJsonToBean(APIType.GET_SERVICE_TYPE, mBean.getJson());
                        if (mServiceTypeResponse != null) {
                            if(mServiceTypeResponse.getStatus() != null && mServiceTypeResponse.getStatus().equalsIgnoreCase("OK")){
                                services = null;
                                services = mServiceTypeResponse.getResults();
                                shoServiceType(APIType.GET_SERVICE_TYPE,services);
                            }else{
                                String errorMsg = mServiceTypeResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mServiceTypeResponse.getError_message();
                                showToast(errorMsg);
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                        }
                        break; */
                    case GET_CIRCLE_TYPE:
                        ServiceTypeResponse mCircleType = (ServiceTypeResponse) JsonParser.getJsonToBean(APIType.GET_SERVICE_TYPE, mBean.getJson());
                        if (mCircleType != null) {
                            if(mCircleType.getStatus() != null && mCircleType.getStatus().equalsIgnoreCase("OK") && mCircleType.getResults().size() > 0){
                                circles = null;
                                circles = mCircleType.getResults();
                                shoServiceType(APIType.GET_CIRCLE_TYPE,circles);
                            }else if(mCircleType.getStatus() != null && mCircleType.getStatus().equalsIgnoreCase("REQUEST_DENIED")
                                    ||mCircleType.getStatus() != null && mCircleType.getStatus().equalsIgnoreCase("SESSION_EXPIRED") ){
                                String errorMsg = mCircleType.getError_message() == null ? getString(R.string.unexpected_server_error): mCircleType.getError_message();
                                //TODO: Logout from here
                                AppUtil.logOut(MapsActivity.this);
                                showToast(errorMsg);
                            }else{
                                String errorMsg = mCircleType.getError_message() == null ? getString(R.string.unexpected_server_error): mCircleType.getError_message();
                                showToast(errorMsg);
                                circles = new ArrayList<>(1);
                                circles.add(getString(R.string.select_circle_type));
                                shoServiceType(APIType.GET_CIRCLE_TYPE,circles);
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                            circles = new ArrayList<>(1);
                            circles.add(getString(R.string.select_circle_type));
                            shoServiceType(APIType.GET_CIRCLE_TYPE,circles);
                        }
                        break;
                    case GET_COST:
                        CostResponse mCostResponse = (CostResponse) JsonParser.getJsonToBean(APIType.GET_COST, mBean.getJson());
                        if (mCostResponse != null) {
                            if(mCostResponse.getStatus() != null && mCostResponse.getStatus().equalsIgnoreCase("OK")){
                                if(mCostResponse.getResults() != null && mCostResponse.getResults().size() > 0) {
                                    reverseGeoCode();
                                    AppConstants.resetCurData();
                                    AppConstants.CUR_BANDWIDTH = Integer.valueOf(sp_speed.getSelectedItem().toString());
                                    AppConstants.CUR_BANDWIDTH_UNIT = sp_speed_type.getSelectedItem().toString();
                                    AppConstants.CUR_SERVICE = sp_service_type.getSelectedItem().toString();
                                    AppConstants.CUR_CIRCLE = sp_circle_type.getSelectedItem().toString();
                                    AppConstants.CUR_SEGMENT = sp_segment_type.getSelectedItem().toString();

                                    AppConstants.CUR_CWW_LOC = sp_cww_loc.getSelectedItem().toString();
                                    AppConstants.CUR_DC_LOC = sp_dc_loc.getSelectedItem().toString();
                                    AppConstants.CUR_LOCATION_END = sp_location_end.getSelectedItem().toString();

                                    AppConstants.CUR_COST_RESULT_LIST = mCostResponse.getResults();
                                    AppConstants.CUR_COST_RESULT = mCostResponse.getResults().get(0);// Assume shorted list is conning based on rank
                                }else{
                                    showToast(mCostResponse.getError_message());
                                }
                            }else if(mCostResponse.getStatus() != null && mCostResponse.getStatus().equalsIgnoreCase("REQUEST_DENIED")
                                    ||mCostResponse.getStatus() != null && mCostResponse.getStatus().equalsIgnoreCase("SESSION_EXPIRED") ){
                                String errorMsg = mCostResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mCostResponse.getError_message();
                                //TODO: Logout from here
                                AppUtil.logOut(MapsActivity.this);
                                showToast(errorMsg);
                            }else{
                                String errorMsg = mCostResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mCostResponse.getError_message();
                                showToast(errorMsg);
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                        }
                        break;

                    case GET_SUMMARY_COUNT:
                        CountResponse mCountResponse = (CountResponse) JsonParser.getJsonToBean(APIType.GET_SUMMARY_COUNT, mBean.getJson());
                        if (mCountResponse != null) {
                            if(mCountResponse.getStatus() != null && mCountResponse.getStatus().equalsIgnoreCase("OK")){
                                AppConstants.TODAY_COUNT = mCountResponse.getResults().getCount();
                                NavBean navBean =  new NavBean(getString(R.string.summary), R.drawable.feedback,R.color.grey_3,AppConstants.TODAY_COUNT);
                                mNavAdapter.arraylist.set(NavAdapter.SUMMARY, navBean);
                                mNavAdapter.notifyDataSetChanged();
                            }else if(mCountResponse.getStatus() != null && mCountResponse.getStatus().equalsIgnoreCase("REQUEST_DENIED")
                                    ||mCountResponse.getStatus() != null && mCountResponse.getStatus().equalsIgnoreCase("SESSION_EXPIRED") ){
                                String errorMsg = mCountResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mCountResponse.getError_message();
                                //TODO: Logout from here
                                AppUtil.logOut(MapsActivity.this);
                                showToast(errorMsg);
                            }else{
                                String errorMsg = mCountResponse.getError_message() == null ? getString(R.string.unexpected_server_error): mCountResponse.getError_message();
                                showToast(errorMsg);
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                        }
                        break;
                    case REVERSE_GEO_CODE:

                        /*
                        if (mBean.getJson() != null) {
                            try {
                                String address = new JSONObject(mBean.getJson()).getJSONArray("results").getJSONObject(0).getString("formatted_address");
                                AppConstants.CUR_ADDRESS = address;
                            } catch (JSONException e) {
                                AppConstants.CUR_ADDRESS ="N/A";
                            }
                        }else{
                            showToast(getString(R.string.unable_to_connect_server));
                        }
                        showHideServiceLayout(false);
                        Intent mIntent = new Intent(MapsActivity.this, FeasibilityDetailActivity.class);
                        startActivity(mIntent);
                        break;

                        */

                    default:
                        break;
                }
            }
            return true;
        }
    });

    private void shoServiceType(APIType type,List<String> results) {
        if(results == null)return;
        switch (type){

            case GET_WIRE_TYPE:
                sp_wire_type.setAdapter(new TypeSpinnerAdapter(this, results));

                break;

            case GET_SPEED_VALUES:
                sp_speed.setAdapter(new TypeSpinnerAdapter(this,results));
                break;
            /*
            case GET_SERVICE_TYPE:
                sp_service_type.setAdapter(new TypeSpinnerAdapter(this, results));
                sp_speed_type.setSelection(1);
                break;
            case GET_CIRCLE_TYPE:
                sp_circle_type.setAdapter(new TypeSpinnerAdapter(this, results));
                break;
            case GET_SEGMENT_TYPE:
                sp_segment_type.setAdapter(new TypeSpinnerAdapter(this, results));
                break;
            case GET_CWW_LOCATION:
                sp_cww_loc.setAdapter(new TypeSpinnerAdapter(this, results));
                break;
            case GET_DC_LOCATION:
                sp_dc_loc.setAdapter(new TypeSpinnerAdapter(this, results));
                break;

            case GET_LOCATION_END:
                sp_location_end.setAdapter(new TypeSpinnerAdapter(this, results));
                break;
                   */
        }
    }
    private void removeTowers(){
        if(towerMarkerList != null) {
            for (int i = 0; i < towerMarkerList.size(); i++) {
                towerMarkerList.get(i).remove();
            }
        }
    }

    private void showDPs(ArrayList<DPResponse.Result> data){

        removePolylines();
        removeTowers();
        if(data == null )return;
        towerMarkerList.clear();
        for (DPResponse.Result tmp : data) {
            if(tmp != null) {
                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.draggable(false);
                markerOpt.icon(BitmapDescriptorFactory.fromBitmap(bitmapSizeByScall(BitmapFactory.decodeResource(getResources(),R.drawable.tower_green),0.4f)));
                markerOpt.position(new LatLng(tmp.getLatitude(), tmp.getLongitude()));
                markerOpt.anchor(0.5f,0.5f);
                Marker marker =  mMap.addMarker(markerOpt);
                marker.setTitle(tmp.getRecordID());
                marker.setSnippet(tmp.getElementID());
                marker.setTag(TAGDP);
                towerMarkerList.add(marker);
                markerMap.put(marker.getId(),tmp);

            }
        }
        showDpProgressDialog.dismiss();
    }

    private void removePolylines(){
        if(ll_feasibility_popup!=null){
            if(ll_feasibility_popup.getVisibility()==View.GONE){
                Log.e("dest marker latlng", destinationMarker.latitude + "," + destinationMarker.longitude);
                Log.e("polyline status", "removed");
                mMap.clear();
                polyline = null;
                dottedPolyline1 = null;
                dottedPolyline2 = null;
                searchMarker = showMarker(R.drawable.marker_red, destinationMarker.latitude, destinationMarker.longitude);
            }
        }
        else {
            Log.e("dest marker latlng", destinationMarker.latitude + "," + destinationMarker.longitude);
            Log.e("polyline status", "removed");
            mMap.clear();
            polyline = null;
            dottedPolyline1 = null;
            dottedPolyline2 = null;
            searchMarker = showMarker(R.drawable.marker_red, destinationMarker.latitude, destinationMarker.longitude);
        }

        //dpRequest(destinationMarker.latitude,destinationMarker.longitude);
        //if(polyline != null){ Log.e("Polyline Status","tag is "+polyline.getTag());polyline.remove();} else Log.e("Polyline Status","Main Polyline is null");
        //if(dottedPolyline1 != null)dottedPolyline1.remove();
        //if(dottedPolyline2 != null)dottedPolyline2.remove();


        /*
        if(polylines!=null){
            for(Polyline line : polylines){
                Log.e("polyline id",line.getId());
                line.remove();
            }
            polylines.clear();
        }
        else Log.e("Polylines status","Nope");
        */
    }


    private void showTowers(ArrayList<TowerResponse.Result> data){
        removePolylines();
        removeTowers();
        if(data == null )return;
        towerMarkerList.clear();
        for (TowerResponse.Result tmp : data) {
            if(tmp != null) {
                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.draggable(false);
                markerOpt.icon(BitmapDescriptorFactory.fromBitmap(bitmapSizeByScall(BitmapFactory.decodeResource(getResources(),R.drawable.tower_green),0.5f)));
                markerOpt.position(new LatLng(tmp.getLatitude(), tmp.getLongitude()));
                markerOpt.anchor(0.5f,0.5f);
                Marker marker =  mMap.addMarker(markerOpt);
                marker.setTitle(tmp.getTower_id());
                marker.setSnippet(tmp.getTower_name());
                towerMarkerList.add(marker);


                //markerMap.put(marker.getId(),tmp);
            }
        }
    }


    public Bitmap bitmapSizeByScall(Bitmap bitmapIn, float scall_zero_to_one_f) {

        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmapIn,
                Math.round(bitmapIn.getWidth() * scall_zero_to_one_f),
                Math.round(bitmapIn.getHeight() * scall_zero_to_one_f), false);

        return bitmapOut;
    }

    private void showFeasibleTowers(ArrayList<CostResponse.Result> data){
        removePolylines();
        if(towerMarkerList != null) {
            for (int i = 0; i < towerMarkerList.size(); i++) {
                towerMarkerList.get(i).remove();
            }
        }
        if(data == null )return;
        towerMarkerList.clear();
        for (CostResponse.Result tmp : data) {
            if(tmp != null) {
                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.draggable(false);
                markerOpt.icon(BitmapDescriptorFactory.fromBitmap(bitmapSizeByScall(BitmapFactory.decodeResource(getResources(),R.drawable.tower_green),0.5f)));
                markerOpt.position(new LatLng(tmp.getLatitude(), tmp.getLongitude()));
                Marker marker =  mMap.addMarker(markerOpt);
                markerOpt.anchor(0.5f,0.5f);
                marker.setTitle(tmp.getTower_id());
                marker.setSnippet(tmp.getTower_name());
                towerMarkerList.add(marker);
                // markerMap.put(marker.getId(),tmp);
            }
        }
    }

    private void drawLine(double lat, double lng){
        LatLng origin = new LatLng(AppConstants.LAT, AppConstants.LNG);
        LatLng dest = new LatLng(lat, lng);
        polyline = mMap.addPolyline(new PolylineOptions().add(origin, dest).width(5).color(Color.RED));
    }

    private void updateLocation(double lat,double lng){
        AppConstants.LAT = lat;
        AppConstants.LNG = lng;
    }
    LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (mMap != null && !isCurrentLoc) {
                if(searchMarker != null)searchMarker.remove();
                searchMarker = showMarker(R.drawable.marker_red,location.getLatitude(), location.getLongitude());
                moveCamera(location.getLatitude(), location.getLongitude());
                updateLocation(location.getLatitude(), location.getLongitude());
                isCurrentLoc = true;
            }
           /* if(previousLocation != null) {
                Log.i(TAG, "Distance:" + location.distanceTo(previousLocation));
            }*/
            /*LatLng mLatLng = new LatLng(location.getLatitude(), location.getLongitude());
            if(geoFenceCircle != null)geoFenceCircle.remove();
            geoFenceCircle = createGeoFenceCircle(mLatLng,AppConstants.RADIUS);
            if(previousLocation != null &&  location.distanceTo(previousLocation) < 50){
                Log.i(TAG, "Distance:" + location.distanceTo(previousLocation));
            return;
            }
            if (googleMap != null) {
                moveCamera(location.getLatitude(), location.getLongitude());
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
                //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
                //isCurrentLoc = true;
                //LatLngBounds bBox = getBounds(mLatLng,AppConstants.RADIUS);
                getBuildingData(getBounds(mLatLng,AppConstants.RADIUS),curPinCode);
            }
            previousLocation = location;*/
        }
    };

    GoogleMap.OnCameraChangeListener mOnCameraChangeListener = new GoogleMap.OnCameraChangeListener() {
        @Override
        public void onCameraChange(CameraPosition cameraPosition) {
            if((boolean)tv_tower.getTag()) {
                //destinationMarker=new LatLng(cameraPosition.target.latitude,cameraPosition.target.longitude);
                if(ll_feasibility_popup!=null){
                    if(ll_feasibility_popup.getVisibility()==View.GONE) dpRequest(destinationMarker.latitude,destinationMarker.longitude);

                }

                else if(ll_dp_mini_info.getVisibility()==View.GONE&&polyline==null){
                    dpRequest(destinationMarker.latitude,destinationMarker.longitude);

                }
                clickMarkerBtnState(false);
            }

        }
    };


    AdapterView.OnItemClickListener mOnItemClickListener=new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            //mMap.clear();

            removePolylines();
            TextView lat=(TextView)findViewById(R.id.list_lat);
            TextView lng=(TextView)findViewById(R.id.list_lng);

            Double latitude=Double.parseDouble(lat.getText().toString());
            Double longitude=Double.parseDouble(lng.getText().toString());
            startMarker=new LatLng(latitude,longitude);



            //if(polyline != null)polyline.remove();

            getPath(latitude,longitude,destinationMarker.latitude,destinationMarker.longitude);
            showDpMiniInfoLayout(false);

            MarkerOptions markerOpt = new MarkerOptions();
            markerOpt.draggable(false);
            markerOpt.icon(BitmapDescriptorFactory.fromBitmap(bitmapSizeByScall(BitmapFactory.decodeResource(getResources(),R.drawable.tower_purple),0.4f)));
            markerOpt.position(new LatLng(startMarker.latitude,startMarker.longitude));
            markerOpt.anchor(0.5f,0.5f);
            Marker marker =  mMap.addMarker(markerOpt);
            marker.setTag(TAGDP);
            towerMarkerList.add(marker);

        }
    };

    private void assignPopupWindow(boolean isFeasible){
        if(isFeasible){
            ll_feasibility_popup=ll_feasible_true;
        }
        else{
            ll_feasibility_popup=ll_feasible_false;
        }

        showFeasiblePopup(true);

    }


    private void updateLatLngBounds(double fromLat,double fromLng,double toLat,double toLng){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(fromLat, fromLng));
        builder.include(new LatLng(toLat,toLng));
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 200));
    }

    private void resizeMapFragmentFull(Boolean bool){
        ViewGroup.MarginLayoutParams parms = (ViewGroup.MarginLayoutParams) mapFragment.getView().getLayoutParams();
        RelativeLayout.LayoutParams params=(RelativeLayout.LayoutParams)lepton_logo.getLayoutParams();
        if(bool){
            parms.bottomMargin=0;
            params.bottomMargin=0;
        }
        else{
            if(ll_feasibility_popup!=null&&ll_feasibility_popup.getVisibility()==View.VISIBLE){
                Log.e("hohohohohoho height",""+ll_feasibility_popup.getHeight());
                parms.bottomMargin = ll_feasibility_popup.getHeight();
                params.bottomMargin= ll_feasibility_popup.getHeight();
            }

            else if(ll_dp_mini_info!=null&&ll_dp_mini_info.getVisibility()==View.VISIBLE){
                parms.bottomMargin = ll_dp_mini_info.getLayoutParams().height;
                params.bottomMargin= ll_dp_mini_info.getLayoutParams().height;
            }



        }
        mapFragment.getView().setLayoutParams(parms);
        lepton_logo.setLayoutParams(params);


    }




    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.tv_current_loc:
                    removePolylines();
                    if(ll_feasibility_popup!=null)showFeasiblePopup(false);
                    showHideServiceLayout(false);
                    showDpMiniInfoLayout(false);
                    clickMarkerBtnState(false);
                    showMyLocation();
                    break;

                case R.id.tv_sat_view:
                    if(ll_feasibility_popup!=null)showFeasiblePopup(false);
                    showHideServiceLayout(false);
                    showDpMiniInfoLayout(false);
                    if((boolean)tv_sat_view.getTag()){
                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        tv_sat_view.setTag(false);
                    }
                    else{
                    mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                    tv_sat_view.setTag(true);
                    }
                    break;
                //case R.id.tv_tower:
                case R.id.iv_tower:
                    if(ll_feasibility_popup!=null)showFeasiblePopup(false);
                    showHideServiceLayout(false);
                    showDpMiniInfoLayout(false);
                    clickMarkerBtnState(false);
                    if((boolean)tv_tower.getTag()){
                        removeTowers();
                        removePolylines();
                        tv_tower.setTag(false);
                        tv_tower.setTag(false);
                        tv_tower.setTextColor(getResources().getColor(R.color.grey_1));
                        iv_tower.setImageResource(R.drawable.tower_grey);
                    }else {
                        tv_tower.setTag(true);
                        tv_tower.setTextColor(getResources().getColor(R.color.secondaryGreen2));
                        iv_tower.setImageResource(R.drawable.tower_green);
                        //towerRequest(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
                        //Log.e("yolo",destinationMarker.latitude+","+destinationMarker.longitude);
                        if(destinationMarker.latitude==DEFAULT_LAT){
                            showMyLocation();
                        }
                        dpRequest(destinationMarker.latitude,destinationMarker.longitude);
                        //destinationMarker=new LatLng(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
                        //showMyLocation();


                    }
                    break;

                case R.id.tv_popup_cross1:
                    Log.e("cross1","clicked");
                    showFeasiblePopup(false);
                    break;

                case R.id.tv_popup_cross2:
                    Log.e("cross2","clicked");
                    showFeasiblePopup(false);
                    break;

                case R.id.btn_feasibility:
                    //TODO: Check if results found before
                    if(isValidData()){
                        //costRequest();
                        showHideServiceLayout(false);

                        //dpRequest(destinationMarker.latitude,destinationMarker.longitude);
                        if(ll_feasibility_popup!=null)showFeasiblePopup(false);
                        showDpMiniInfoLayout(true);
                    }
                    break;
                case R.id.btn_cancel:
                    if(ll_feasibility_popup!=null)showFeasiblePopup(false);
                    showHideServiceLayout(false);

                    break;
                case R.id.tv_click_to_check:

                    /*
                    if(AppConstants.INTENT_FROM.equalsIgnoreCase(CheckFeasibilityActivity.class.getSimpleName())){
                        Intent mIntent = new Intent(MapsActivity.this, CheckFeasibilityActivity.class);
                        startActivity(mIntent);
                    }else if(AppConstants.INTENT_FROM.equalsIgnoreCase(FeasibilityDetailActivity.class.getSimpleName())){
                        Intent mIntent = new Intent(MapsActivity.this, FeasibilityDetailActivity.class);
                        startActivity(mIntent);


                    }

                    else{
                        clickMarkerBtnState(false);
                    }
                    break;

                    */
            }
        }
    };

    GoogleMap.OnMarkerClickListener markerClickListener = new GoogleMap.OnMarkerClickListener() {
        @Override
        public boolean onMarkerClick(Marker marker) {
            //showToast(marker.getId());showHideServiceLayout(true);
            if(ll_feasibility_popup!=null)showFeasiblePopup(false);
            if(searchMarker != null && marker.getId().equalsIgnoreCase(searchMarker.getId())){
               /* if(services == null) {
                    serviceTypeRequest();
                }else{
                    shoServiceType(APIType.GET_SERVICE_TYPE,services);
                }*/
                //circleTypeRequest();
                /*if(circles == null) {

                }else{
                    shoServiceType(APIType.GET_CIRCLE_TYPE,circles);
                }*/

                String[] wireArray=getResources().getStringArray((R.array.wire_type));
                List wireList= Arrays.asList(wireArray);
                shoServiceType(APIType.GET_WIRE_TYPE,wireList);

                String[] speedArray=getResources().getStringArray((R.array.speed_val));
                List speedList= Arrays.asList(speedArray);
                shoServiceType(APIType.GET_SPEED_VALUES,speedList);

                String[] serviceArray = getResources().getStringArray(R.array.service_type);
                List serviceList = Arrays.asList(serviceArray);
                shoServiceType(APIType.GET_SERVICE_TYPE,serviceList);

                String[] segmentArray = getResources().getStringArray(R.array.business_seg_type);
                List segmentList = Arrays.asList(segmentArray);
                shoServiceType(APIType.GET_SEGMENT_TYPE,segmentList);

                String[] yesNoArray = getResources().getStringArray(R.array.yes_no_array);
                List yesNoList = Arrays.asList(yesNoArray);
                shoServiceType(APIType.GET_CWW_LOCATION,yesNoList);
                shoServiceType(APIType.GET_DC_LOCATION,yesNoList);

                String[] locationEndArray = getResources().getStringArray(R.array.location_end);
                List locationEndList = Arrays.asList(locationEndArray);
                shoServiceType(APIType.GET_LOCATION_END,locationEndList);

                showHideServiceLayout(true);

            }else{

                removePolylines();
                marker.showInfoWindow();
                startMarker=marker.getPosition();
                showHideServiceLayout(false);
                getPath(marker.getPosition().latitude,marker.getPosition().longitude,destinationMarker.latitude,destinationMarker.longitude);
                showDpMiniInfoLayout(false);


                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.draggable(false);
                markerOpt.icon(BitmapDescriptorFactory.fromBitmap(bitmapSizeByScall(BitmapFactory.decodeResource(getResources(),R.drawable.tower_purple),0.4f)));
                markerOpt.position(new LatLng(startMarker.latitude,startMarker.longitude));
                markerOpt.anchor(0.5f,0.5f);
                Marker mMarker =  mMap.addMarker(markerOpt);
                mMarker.setTag(TAGDP);
                towerMarkerList.add(mMarker);
            }
            return true;
        }
    };


    private boolean isValidData(){
        if(sp_speed.getSelectedItem().toString().length() == 0 || Integer.valueOf(sp_speed.getSelectedItem().toString()) == 0){
            showToast(getString(R.string.enter_valid_speed));
            return false;
        }

        /*if(sp_speed_type.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select_speed_type))){
            showToast(getString(R.string.select_speed_type));
            return false;

        }if(sp_service_type.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select_service_type))){
            showToast(getString(R.string.select_service_type));
            return false;
        }if(sp_circle_type.getSelectedItem().toString().equalsIgnoreCase(getString(R.string.select_circle_type))){
            showToast(getString(R.string.select_circle_type));
            return false;
        }
        */
        return true;
    }

    @Override
    public void onBackPressed() {

        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        else if(ll_feasibility_popup!=null&&ll_feasibility_popup.getVisibility()==View.VISIBLE){
            if(ll_feasibility_popup!=null)showFeasiblePopup(false);
        }
        else if(ll_dp_mini_info.getVisibility()==View.VISIBLE){
            showDpMiniInfoLayout(false);
        }else if(ll_service_layout.getVisibility() == View.VISIBLE){
            ll_service_layout.setVisibility(View.GONE);
        }else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        NavBean navBean =  new NavBean(getString(R.string.summary), R.drawable.feedback,R.color.grey_3,AppConstants.TODAY_COUNT);
        mNavAdapter.arraylist.set(NavAdapter.SUMMARY, navBean);
        mNavAdapter.notifyDataSetChanged();

        /*
        if(AppConstants.INTENT_FROM.equalsIgnoreCase(FeasibilityDetailActivity.class.getSimpleName())){
            tv_tower.setTag(false);
            iv_tower.setImageResource(R.drawable.tower_grey);
            removeTowers();
            //TODO: show feasibility result data
            ArrayList<CostResponse.Result> temp = new ArrayList<>(1);
            temp.add(AppConstants.CUR_COST_RESULT);
            showFeasibleTowers(temp);
            drawLine(temp.get(0).getLatitude(),temp.get(0).getLongitude());
            clickMarkerBtnState(true);

        }else if(AppConstants.INTENT_FROM.equalsIgnoreCase(CheckFeasibilityActivity.class.getSimpleName())){
            showFeasibleTowers(AppConstants.CUR_COST_RESULT_LIST);
            clickMarkerBtnState(true);
        }else{
            //TODO: Do nothing.
        }*/
    }

    private void clickMarkerBtnState(boolean isBtnEnable){
        RelativeLayout.LayoutParams mLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if(isBtnEnable){
            mLayoutParams.setMargins(0,0,0,AppUtil.dp2px(this,50));
            tv_click_to_check.setVisibility(View.VISIBLE);
            tv_click_to_check.setText(getString(R.string.go_back));
        }else{
            mLayoutParams.setMargins(0,0,0,0);
            tv_click_to_check.setVisibility(View.GONE);
            tv_click_to_check.setText(getString(R.string.click_marker_to_check_feasibility));
        }
        rl_map_container.setLayoutParams(mLayoutParams);
    }


}
