package com.leptonmaps.wirelinefeasibility.datamodels;

/**
 * Created by Hardik bansal on 02/06/2017.
 */

public class NavBean {
    private String title;
    private int icon;
    private int indicator_color;
    private int count;
    public NavBean(String title, int icon, int indicator_color,int count){
        this.title = title;
        this.icon = icon;
        this.indicator_color = indicator_color;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIndicator_color() {
        return indicator_color;
    }

    public void setIndicator_color(int indicator_color) {
        this.indicator_color = indicator_color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
