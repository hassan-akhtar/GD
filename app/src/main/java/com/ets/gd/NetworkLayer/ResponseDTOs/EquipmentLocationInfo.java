package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class EquipmentLocationInfo extends RealmObject {

    private String LocationType;
    private String Location;
    private int LocationID;
    private int JobNumberID;
    private int EquipmentID;

    public String getLocationType() {
        return LocationType;
    }

    public void setLocationType(String locationType) {
        LocationType = locationType;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public int getJobNumberID() {
        return JobNumberID;
    }

    public void setJobNumberID(int jobNumberID) {
        JobNumberID = jobNumberID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }
}
