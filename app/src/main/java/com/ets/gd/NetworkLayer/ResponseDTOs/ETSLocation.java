package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class ETSLocation extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private int CustomerID;
    private String Floor;
    private String Room;
    private int SiteID;
    private int BuildingID;
    private int LocationTypeID;
    private boolean isActive;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
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

    public int getLocationTypeID() {
        return LocationTypeID;
    }

    public void setLocationTypeID(int locationTypeID) {
        LocationTypeID = locationTypeID;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}

