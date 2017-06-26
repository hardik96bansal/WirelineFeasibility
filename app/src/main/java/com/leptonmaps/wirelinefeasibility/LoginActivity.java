package com.leptonmaps.wirelinefeasibility;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leptonmaps.wirelinefeasibility.appnetwork.AsyncThread;
import com.leptonmaps.wirelinefeasibility.appnetwork.ReqRespBean;
import com.leptonmaps.wirelinefeasibility.appnetwork.WEBAPI;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.apputil.SharePrefUtil;
import com.leptonmaps.wirelinefeasibility.enums.APIType;
import com.leptonmaps.wirelinefeasibility.jsonparser.JsonParser;
import com.leptonmaps.wirelinefeasibility.response.TokenResponse;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int RC_SIGN_IN = 1000;
    private static final String TAG = LoginActivity.class.getSimpleName();
    private TextView tv_password, tv_username;
    private EditText et_password, et_username;
    private Resources mResources = null;
    private Button btn_login;
    private Typeface icomoon, vodafoneRg,vodafoneRgBd = null;
    private LinearLayout ll_logo_root = null;
    private AsyncThread mAsyncThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        icomoon = AppUtil.getIcomoonTypeface(this);
        vodafoneRg = AppUtil.getVodafoneRg(this);
        vodafoneRgBd = AppUtil.getVodafoneRgBd(this);
        mResources = getResources();
        initUI();
        setTypefaces();
        setListeners();

    }

    public void initUI() {
        tv_password = (TextView) findViewById(R.id.tv_password);
        tv_username = (TextView) findViewById(R.id.tv_username);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_password = (EditText) findViewById(R.id.et_password);
        et_username = (EditText) findViewById(R.id.et_username);
        TextView mTvVersionName = (TextView) findViewById(R.id.tv_version_text);
        mTvVersionName.setTypeface(vodafoneRgBd);
        mTvVersionName.setText("Version " + AppUtil.getAppVersionString(getApplicationContext()) + " " +getString(R.string.beta));
        ll_logo_root = (LinearLayout) findViewById(R.id.ll_logo_root);

    }


    public void setListeners() {
        btn_login.setOnClickListener(this);
       /* et_password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    if (isValidData()) {
                        hideKeyboard(LoginActivity.this, et_password);
                        loginRequest(et_username.getText().toString().trim(), et_password.getText().toString().trim());
                    }
                }
                return true;
            }
        });*/

    }

    public static void hideKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void setTypefaces() {
        tv_password.setTypeface(icomoon);
        tv_username.setTypeface(icomoon);
        et_password.setTypeface(vodafoneRgBd);
        et_username.setTypeface(vodafoneRgBd);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (isValidData()) {
                    //hideKeyboard(LoginActivity.this, et_password);


                    loginRequest(et_username.getText().toString().trim(), et_password.getText().toString().trim());
                    //SharePrefUtil.setUserId(LoginActivity.this,et_username.getText().toString().trim());
                    //SharePrefUtil.setToken(LoginActivity.this,"Default Token");
                    //gotoNextActivity(MapsActivity.class);

                }
                break;
        }
    }

    private boolean isValidData() {
       /* if(sp_user_type.getSelectedItem() instanceof UserTypeBean &&
                ((UserTypeBean)sp_user_type.getSelectedItem()).getUserType()
                        .equalsIgnoreCase(getString(R.string.select_user_type))){
            showSnackBar(getString(R.string.select_user_type),"");
            return false;
        }*/
        if (et_username.getText().toString().trim().length() == 0) {
            showSnackBar(getString(R.string.enter_username), "");
            return false;
        }
        if (et_password.getText().toString().trim().length() == 0) {
            showSnackBar(getString(R.string.enter_password), "");
            return false;
        }
        return true;
    }

    private void showSnackBar(String msg, String actionTxt) {
        /*Snackbar bar = Snackbar.make(ll_logo_root, msg, Snackbar.LENGTH_LONG)
                .setAction(actionTxt, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:
                    }
                });
        bar.show();
        bar.setActionTextColor(Color.RED);*/
        Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void gotoNextActivity(Class<? extends Activity> nextUI) {
        Intent i = new Intent(this, nextUI);
        startActivity(i);
        LoginActivity.this.finish();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void loginRequest(String userId, String pwd) {
      /*  LoginReq mLoginReq = new LoginReq();
        mLoginReq.setUsername(userId);
        mLoginReq.setPassword(pwd);
        mLoginReq.setGrant_type("password");*/
        // JSONObject json = JsonParser.getBeanToJson(mLoginReq);
        ReqRespBean mBean = new ReqRespBean();
        StringBuilder mStringBuilder = new StringBuilder("username=");
        mStringBuilder.append(AppUtil.base64Encode(userId));
        mStringBuilder.append("&password=");
        mStringBuilder.append(AppUtil.base64Encode(pwd));
        mStringBuilder.append("&grant_type=password");
        // if (json != null) {
        //mBean.setJson(json.toString());
        mBean.setJson(mStringBuilder.toString());
        // }
        mBean.setApiType(APIType.LOGIN);
        mBean.setRequestmethod(WEBAPI.POST);
        mBean.setUrl(WEBAPI.getWEBAPI(APIType.LOGIN));
        mBean.setCtx(this);
        mBean.setMimeType("application/x-www-form-urlencoded");
        mBean.setmHandler(mHandler);
        mAsyncThread = null;
        mAsyncThread = new AsyncThread();
        mAsyncThread.initProgressDialog(LoginActivity.this, mOnCancelListener);
        mAsyncThread.execute(mBean);
    }

    DialogInterface.OnCancelListener mOnCancelListener = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            if (mAsyncThread != null) mAsyncThread.cancel(true);
            mAsyncThread = null;
        }
    };

    Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mAsyncThread = null;
            if (msg.obj == null) {
                return false;

            } else {
                ReqRespBean mBean = (ReqRespBean) msg.obj;
                Log.i(TAG, "mHandler :Resp Json:" + mBean.getJson());
                switch (mBean.getApiType()) {
                    case LOGIN:
                        TokenResponse mTokenResponse = (TokenResponse) JsonParser.getJsonToBean(APIType.LOGIN, mBean.getJson());
                        if (mTokenResponse != null) {
                            if(mTokenResponse.getAccess_token() != null && mTokenResponse.getAccess_token().length() > 0){
                                //showSnackBar(getString(R.string.login_successful),"");
                                //TODO: user details request
                                // if(chk_remind_me.isChecked()){
                                // SharePrefUtil.setRemindMe(LoginActivity.this,true);
                                // }
                                SharePrefUtil.setUserId(LoginActivity.this,et_username.getText().toString().trim());
                                SharePrefUtil.setToken(LoginActivity.this,mTokenResponse.getAccess_token());
                                gotoNextActivity(MapsActivity.class);
                            }else{
                                //String errorMsg = mCommonResp.getStatus() == null ? getString(R.string.unexpected_server_error): mCommonResp.getStatus();
                                showSnackBar(getString(R.string.unable_to_get_token),"");
                            }
                        }else{
                            showSnackBar(getString(R.string.unable_to_connect_server),"");
                        }
                        break;

                    default:
                        break;
                }
            }
            return true;
        }
    });

    @Override
    public void onBackPressed() {
        if (mAsyncThread != null && mAsyncThread.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncThread.cancel(true);
        } else {
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAsyncThread != null && mAsyncThread.getStatus() == AsyncTask.Status.RUNNING) {
            mAsyncThread.cancel(true);
        }
    }



}

