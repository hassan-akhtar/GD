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
    private String JobNumberID;
    private String EquipmentID;

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

    public String getJobNumberID() {
        return JobNumberID;
    }

    public void setJobNumberID(String jobNumberID) {
        JobNumberID = jobNumberID;
    }

    public String getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(String equipmentID) {
        EquipmentID = equipmentID;
    }
}
