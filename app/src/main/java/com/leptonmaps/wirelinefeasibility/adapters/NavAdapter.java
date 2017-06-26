package com.leptonmaps.wirelinefeasibility.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.leptonmaps.wirelinefeasibility.apputil.AppUtil;
import com.leptonmaps.wirelinefeasibility.datamodels.NavBean;
import com.leptonmaps.wirelinefeasibility.R;

import java.util.ArrayList;

/**
 * Created by Hardik bansal on 02/06/2017.
 */


@SuppressLint("ResourceAsColor")
public class NavAdapter extends BaseAdapter {
    // Declare Variables
    private Context mContext;
    private LayoutInflater mInflater;
    public ArrayList<NavBean> arraylist;
    private Typeface typeface = null;
    //public static final int ASSIGNED_BEAT = 0;
    // public static final int PENDING_BEAT = 1;
    // public static final int COMPLETED_BEAT = 2;
    //public static final int HOME = 0;
    // public static final int TICKETS = 1;
    //public static final int NOTIFICATIONS = 2;
    public static final int HOME = 0;
    public static final int SUMMARY = 1;
    public static final int LOGOUT = 2;
    public NavAdapter(Context mContext, ArrayList<NavBean> layerInfo) {
        this.mContext = mContext;
        this.arraylist = layerInfo;
        mInflater = LayoutInflater.from(mContext);
        typeface = AppUtil.getVodafoneRgBd(mContext);
    }

    public class ViewHolder {
        TextView title;
        TextView count;
        ImageView iv_icon;
        View view_indicator;
    }

    @Override
    public int getCount() {
        return arraylist.size();
    }

    @Override
    public NavBean getItem(int position) {
        return arraylist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.drawer_list_row, null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
            holder.count = (TextView)convertView.findViewById(R.id.tv_count);
            holder.view_indicator = (View)convertView.findViewById(R.id.view_indicator);
            holder.count.setTypeface(typeface);
            holder.title.setTypeface(typeface);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText((arraylist.get(position)).getTitle());
        if(position == NavAdapter.SUMMARY) {
            String count = "0";
            if((arraylist.get(position)).getCount() > 99){
                count = "99+";
            }else {
                count = String.valueOf((arraylist.get(position)).getCount());
            }
            holder.count.setText(count);
            holder.count.setVisibility(View.VISIBLE);
        }else{
            holder.count.setVisibility(View.GONE);
        }
        holder.iv_icon.setImageResource(arraylist.get(position).getIcon());
        holder.view_indicator.setBackgroundColor(mContext.getResources().getColor(arraylist.get(position).getIndicator_color()));
        holder.view_indicator.setVisibility(View.GONE);
        // if (position == 0) convertView.setBackgroundResource(R.drawable.drawerlist_selector);
        return convertView;
    }

	/*private void initRippleEffect(View view) {
        // static initialization
		RippleBuilder mRippleBuilder = MaterialRippleLayout.on(view);
		mRippleBuilder.rippleColor(Color.parseColor("#FF0000"));
		mRippleBuilder.rippleAlpha(0.2f);
		mRippleBuilder.rippleHover(true);
		mRippleBuilder.create();
	}*/

    public void setIndicator(int pos,int color){
        for (int i=0; i< arraylist.size(); i++){
            arraylist.get(i).setIndicator_color(R.color.grey_3);
        }
        arraylist.get(pos).setIndicator_color(color);
        notifyDataSetChanged();
    }
}
