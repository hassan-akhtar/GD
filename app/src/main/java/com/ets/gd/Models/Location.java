package com.ets.gd.Models;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Location extends RealmObject implements Serializable{

    private String locationID;
    private String description;
    private String vendor;
    private String agent;
    private String place;

    public Location() {

    }

    public Location(String locationID, String description, String vendor, String agent, String place) {
        this.locationID = locationID;
        this.description = description;
        this.vendor = vendor;
        this.agent = agent;
        this.place = place;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getLocationID() {
        return locationID;
    }

    public void setLocationID(String locationID) {
        this.locationID = locationID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }
}
