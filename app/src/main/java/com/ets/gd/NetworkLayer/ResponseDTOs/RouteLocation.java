package com.ets.gd.NetworkLayer.ResponseDTOs;


import com.ets.gd.NetworkLayer.ResponseDTOs.RouteAsset;

import java.io.Serializable;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class RouteLocation extends RealmObject implements Serializable {

    private int ID;
    private int LocationOrder;
    private int RouteID;
    private int LocationID;
    private int SiteID;
    private int BuildingID;
    private RealmList<RouteAsset> RouteAssets;

    public RouteLocation() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getLocationOrder() {
        return LocationOrder;
    }

    public void setLocationOrder(int locationOrder) {
        LocationOrder = locationOrder;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public int getSiteID() {
        return SiteID;
    }

    public void setSiteID(int siteID) {
        SiteID = siteID;
    }

    public int getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(int buildingID) {
        BuildingID = buildingID;
    }

    public RealmList<RouteAsset> getRouteAssets() {
        return RouteAssets;
    }

    public void setRouteAssets(RealmList<RouteAsset> routeAssets) {
        RouteAssets = routeAssets;
    }
}
