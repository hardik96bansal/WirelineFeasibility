package com.leptonmaps.wirelinefeasibility.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.leptonmaps.wirelinefeasibility.R;
import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.response.DPResponse;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 08/06/2017.
 */

public class DpListAdapter extends BaseAdapter {

    private Context mcontext;
    private ArrayList<DPResponse.Result> results;

    public DpListAdapter(Context mcontext, ArrayList<DPResponse.Result> results){
        this.mcontext=mcontext;
        this.results=results;
    }


    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater=(LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.list_item_dpinfo,viewGroup,false);
        TextView elementId=(TextView)row.findViewById(R.id.tv_element_id);
        TextView recordId=(TextView)row.findViewById(R.id.tv_record_id);
        TextView recId=(TextView)row.findViewById(R.id.tv_rec_id);
        TextView elemId=(TextView)row.findViewById(R.id.tv_elem_id);
        TextView lat=(TextView)row.findViewById(R.id.list_lat);
        TextView lng=(TextView)row.findViewById(R.id.list_lng);


        Typeface vodafoneRgBd =  AppUtil.getVodafoneRgBd(mcontext);

        elementId.setTypeface(vodafoneRgBd);
        elemId.setTypeface(vodafoneRgBd);
        recId.setTypeface(vodafoneRgBd);
        recordId.setTypeface(vodafoneRgBd);

        DPResponse.Result dpResponseResult=results.get(i);
        elementId.setText(dpResponseResult.getElementID());
        recordId.setText(dpResponseResult.getRecordID());
        lat.setText(""+dpResponseResult.getLatitude());
        lng.setText(""+dpResponseResult.getLongitude());
        return row;
    }
}
