package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/4/2016.
 */
public class NavDrawerItem {
    private boolean showNotify;
    private String title;
    private int icon;


    public NavDrawerItem() {

    }

    public NavDrawerItem(boolean showNotify, String title, int icon) {
        this.showNotify = showNotify;
        this.title = title;
        this.icon = icon;
    }

    public boolean isShowNotify() {
        return showNotify;
    }

    public void setShowNotify(boolean showNotify) {
        this.showNotify = showNotify;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
