package com.ets.gd.Models;

/**
 * Created by hakhtar on 5/26/2017.
 * General Data
 */

public class ToolhawkAsset {

    private String code;
    private String name;
    private String loc;
    private boolean isParent;


    public ToolhawkAsset() {
    }

    public ToolhawkAsset(String code, String name, String loc, boolean isParent) {
        this.code = code;
        this.name = name;
        this.loc = loc;
        this.isParent = isParent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }
}
