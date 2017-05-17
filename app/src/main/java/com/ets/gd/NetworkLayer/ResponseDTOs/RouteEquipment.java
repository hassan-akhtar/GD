package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/17/2017.
 * General Data
 */

public class RouteEquipment extends RealmObject{

    private int ID;
    private String DeviceTypeID;
    private int RouteID;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(String deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }
}
