package com.ets.gd.Models;


import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RouteLocation extends RealmObject implements Serializable {

    private String locationID;
    private String description;
    private String site;
    private String building;
    private String place;
    private RealmList<Asset> routeAssets;

    public RouteLocation() {

    }

    public RouteLocation(String locationID, String description, String site, String building, String place, RealmList<Asset> routeAssets) {
        this.locationID = locationID;
        this.description = description;
        this.site = site;
        this.building = building;
        this.place = place;
        this.routeAssets = routeAssets;
    }

    public List<Asset> getRouteAssets() {
        return routeAssets;
    }

    public void setRouteAssets(RealmList<Asset> routeAssets) {
        this.routeAssets = routeAssets;
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

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }
}
