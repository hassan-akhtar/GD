package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RouteAsset extends RealmObject {

    @PrimaryKey
    private int ID;
    private int Quantity;
    private int LocationID;
    private int EquipmentID;
    private int RouteID;
    private String InspectionType;
    private int RouteLocationID;
    private int DeviceTypeID;
    private boolean isInspected;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getQuantity() {
        return Quantity;
    }

    public boolean isInspected() {
        return isInspected;
    }

    public void setInspected(boolean inspected) {
        isInspected = inspected;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public String getInspectionType() {
        return InspectionType;
    }

    public void setInspectionType(String inspectionType) {
        InspectionType = inspectionType;
    }

    public int getRouteLocationID() {
        return RouteLocationID;
    }

    public void setRouteLocationID(int routeLocationID) {
        RouteLocationID = routeLocationID;
    }

    public int getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(int deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }
}
