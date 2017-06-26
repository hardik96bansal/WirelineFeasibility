package com.leptonmaps.wirelinefeasibility;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.leptonmaps.wirelinefeasibility.interfaces.ExchangeData;
import com.leptonmaps.wirelinefeasibility.adapters.NavAdapter;
import com.leptonmaps.wirelinefeasibility.datamodels.NavBean;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.apputil.SharePrefUtil;
import com.leptonmaps.wirelinefeasibility.constants.AppConstants;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class BaseActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ExchangeData {
    public static final int INTENT_CHECK_FEASIBILITY_ACT = 6 ;
    public static final int INTENT_CHECK_FEASIBILITY_SAVE_ACT = 7 ;
    protected Toolbar toolbar = null;
    protected DrawerLayout drawer = null;
    protected LinearLayout ll_view_container = null;
    // protected NavigationView navigationView = null;
    protected LayoutInflater mLayoutInflater = null;
    //private FragmentManager fragmentManager  = null;
    // protected FragmentManager mSupportFragmentMgr;
    protected TextView tv_user,tv_email;
    protected ImageView iv_profile;
    protected Typeface icoMoon,vodafoneRgBd,vodafoneRg;
    protected NumberFormat mNumberFormat = null;
    protected ArrayList<NavBean> navList = null;
    protected NavAdapter mNavAdapter = null;
    protected ListView lv_nav = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        icoMoon = AppUtil.getIcomoonTypeface(this);
        vodafoneRgBd =  AppUtil.getVodafoneRgBd(this);
        vodafoneRg =  AppUtil.getVodafoneRg(this);
        mLayoutInflater = LayoutInflater.from(this);
        //fragmentManager  = getFragmentManager();
        // mSupportFragmentMgr = getSupportFragmentManager();
        initBaseViews();
        setBaseListener();
        //homeFragment();
        mNumberFormat = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
        setNavListAdapter();
    }

    private void setNavListAdapter() {
        navList = new ArrayList<>();
        navList.add(NavAdapter.HOME, new NavBean(getString(R.string.home), R.drawable.about_app,R.color.grey_3,0));
        navList.add(NavAdapter.SUMMARY, new NavBean(getString(R.string.summary), R.drawable.feedback,R.color.grey_3, AppConstants.TODAY_COUNT));
        navList.add(NavAdapter.LOGOUT, new NavBean(getString(R.string.logout), R.drawable.logout,R.color.grey_3,0));
        mNavAdapter = new NavAdapter(this, navList);
        lv_nav.setAdapter(mNavAdapter);
        tv_user.setText(SharePrefUtil.getUserId(this));
    }

    public void initBaseViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle(AppUtil.getActionbarTitle(getString(R.string.app_name),vodafoneRgBd));

        tv_user = (TextView)findViewById(R.id.tv_user);
        tv_email = (TextView)findViewById(R.id.tv_email);
        iv_profile = (ImageView) findViewById(R.id.iv_profile);
        lv_nav = (ListView)findViewById(R.id.lv_nav);

        /*SpannableStringBuilder title = new SpannableStringBuilder(getString(R.string.app_name));
        title.setSpan (new CustomTypefaceSpan("", vodafoneRgBd), 0, title.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        setTitle(title);*/
        //setTitle("");
        //getSupportActionBar().setIcon(R.drawable.ic_logo);
        //getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.drawable.actionbar_bg));

      /* Drawable drawable = ResourcesCompat.getDrawable(getResources(), android.R.drawable.ic_menu, null);
        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, Color.BLACK);
        getSupportActionBar().setHomeAsUpIndicator(drawable);*/

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ll_view_container = (LinearLayout) findViewById(R.id.ll_view_container);
       /* navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        Menu m = navigationView.getMenu();
        for (int i=0;i<m.size();i++) {
            MenuItem mi = m.getItem(i);
            //for aapplying a font to subMenu ...
            SubMenu subMenu = mi.getSubMenu();
            if (subMenu!=null && subMenu.size() >0 ) {
                for (int j=0; j <subMenu.size();j++) {
                    MenuItem subMenuItem = subMenu.getItem(j);
                    subMenuItem.setTitle(AppUtil.getActionbarTitle(subMenuItem.getTitle().toString(),vodafoneRgBd));
                }
            }
            //the method we have create in activity
            mi.setTitle(AppUtil.getActionbarTitle(mi.getTitle().toString(),vodafoneRgBd));
        }
        View headerLayout = navigationView.getHeaderView(0);

        tv_user = (TextView)headerLayout.findViewById(R.id.tv_user);
        tv_email = (TextView)headerLayout.findViewById(R.id.tv_email);
        iv_profile = (ImageView) headerLayout.findViewById(R.id.iv_profile);
        tv_user.setText(SharePrefUtil.getUserId(this));
        */
    }

    public void setBaseListener() {
        lv_nav.setOnItemClickListener(mOnItemClickListener);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        // navigationView.setNavigationItemSelectedListener(this);
    }

    /*@Override
    public void onBackPressed() {
        if (drawer != null && drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    /*@Override
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
            MapFragment mapFragment = (MapFragment) getSupportFragmentManager().findFragmentByTag(MapFragment.class.getSimpleName());
            if (mapFragment != null && mapFragment.isVisible()) {
                mapFragment.openAutocompleteActivity();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /*if (id == R.id.nav_settings) {
            *//*FeasibilityDetailFragment mFeasibilityDetailFragment = new FeasibilityDetailFragment();
            FragmentTransaction fragmentTransaction = mSupportFragmentMgr.beginTransaction();
            fragmentTransaction.replace(R.id.ll_view_container, mFeasibilityDetailFragment, FeasibilityDetailFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*//*

        } else*/
       /* if (id == R.id.nav_logout) {
            SharePrefUtil.clearSharePref(this);
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
            return true;
           *//* CheckFeasibilityFragment mCheckFeasibilityFragment = new CheckFeasibilityFragment();
            FragmentTransaction fragmentTransaction = mSupportFragmentMgr.beginTransaction();
            fragmentTransaction.replace(R.id.ll_view_container, mCheckFeasibilityFragment,CheckFeasibilityFragment.class.getSimpleName());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();*//*
        }
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*protected void gotToNextActivity(){

    }*/
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDataPass(Object data) {

    }



    AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            drawer.closeDrawer(Gravity.LEFT);
            switch (position) {

                case NavAdapter.LOGOUT:
                   /* AppUtil.confirmDialog(MainActivity.this, getString(R.string.app_name), getString(R.string.do_you_want_to_logout), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.this.finish();
                        }
                    });*/

                    // mNavAdapter.setIndicator(NavAdapter.LOGOUT,R.color.logout);
                   /* SharePrefUtil.clearSharePref(BaseActivity.this);
                    Intent mIntent = new Intent(BaseActivity.this, LoginActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                    BaseActivity.this.finish();*/
                    AppUtil.logOut(BaseActivity.this);
                    break;
                case NavAdapter.SUMMARY:
                    /*
                    Intent mIntent2 = new Intent(BaseActivity.this, ActivitySummary.class);
                    mIntent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(mIntent2,INTENT_CHECK_FEASIBILITY_ACT);
                    break;*/
                case NavAdapter.HOME:
                    Intent homeIntent = new Intent(BaseActivity.this, MapsActivity.class);
                    startActivity(homeIntent);
                    break;
            }
            drawer.closeDrawer(Gravity.LEFT);
        }
    };

    public void gotoNextActivity(Class<? extends Activity> nextUI) {
        Intent i = new Intent(this, nextUI);
        startActivity(i);
        BaseActivity.this.finish();
    }
}
