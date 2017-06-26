package com.leptonmaps.wirelinefeasibility.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.leptonmaps.wirelinefeasibility.R;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;

import java.util.List;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class TypeSpinnerAdapter extends ArrayAdapter<String> {

    private Context activity;
    public List<String> userTypeBeenList;
    private LayoutInflater inflater;
    private Typeface icomoon,cnn = null;

    public TypeSpinnerAdapter(Context ctx, List<String> userTypeBeenList) {
        super(ctx,0,userTypeBeenList);
        this.activity = ctx;
        this.userTypeBeenList = userTypeBeenList;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        icomoon = AppUtil.getIcomoonTypeface(ctx);
        cnn = AppUtil.getVodafoneRgBd(ctx);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);

    }
    @Override
    public int getCount() {
        return userTypeBeenList.size();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    // This funtion called for each row ( Called data.size() times )
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = inflater.inflate(R.layout.spinner_row, parent, false);
        TextView tv_user_type = (TextView)row.findViewById(R.id.tv_user_type);
        tv_user_type.setText(userTypeBeenList.get(position));
        tv_user_type.setTypeface(cnn);
        TextView tv_ico_user_type = (TextView)row.findViewById(R.id.tv_ico_user_type);
        tv_ico_user_type.setTypeface(icomoon);
        row.setTag(userTypeBeenList.get(position));
        return row;
    }
}
