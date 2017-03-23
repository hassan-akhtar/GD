package com.ets.gd.Models;

/**
 * Created by hakhtar on 3/23/2017.
 * General Data
 */

public class Location {

    private String locID;
    private String desc;
    private String place;

    public Location() {

    }

    public Location(String locID, String desc, String place) {
        this.locID = locID;
        this.desc = desc;
        this.place = place;
    }

    public String getLocID() {
        return locID;
    }

    public void setLocID(String locID) {
        this.locID = locID;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
}
