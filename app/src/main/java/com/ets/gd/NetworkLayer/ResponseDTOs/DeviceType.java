package com.ets.gd.NetworkLayer.ResponseDTOs;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class DeviceType  extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private boolean HasDueDates;
    private boolean HasReadings;
    private RealmList<DeviceTypeStatusCodes> DeviceTypeStatusCodes;

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

    public boolean isHasDueDates() {
        return HasDueDates;
    }

    public void setHasDueDates(boolean hasDueDates) {
        HasDueDates = hasDueDates;
    }

    public boolean isHasReadings() {
        return HasReadings;
    }

    public void setHasReadings(boolean hasReadings) {
        HasReadings = hasReadings;
    }

    public List<com.ets.gd.NetworkLayer.ResponseDTOs.DeviceTypeStatusCodes> getDeviceTypeStatusCodes() {
        return DeviceTypeStatusCodes;
    }

    public void setDeviceTypeStatusCodes(RealmList<com.ets.gd.NetworkLayer.ResponseDTOs.DeviceTypeStatusCodes> deviceTypeStatusCodes) {
        DeviceTypeStatusCodes = deviceTypeStatusCodes;
    }
}
