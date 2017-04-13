package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class DeviceTypeStatusCodes {

    private int  ID;
    private int DeviceTypeID;
    private int StatusCodeID;
    private int MinValue;
    private int MaxValue;
    private StatusCode StatusCode;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(int deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }

    public int getStatusCodeID() {
        return StatusCodeID;
    }

    public void setStatusCodeID(int statusCodeID) {
        StatusCodeID = statusCodeID;
    }

    public int getMinValue() {
        return MinValue;
    }

    public void setMinValue(int minValue) {
        MinValue = minValue;
    }

    public int getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(int maxValue) {
        MaxValue = maxValue;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.StatusCode getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(com.ets.gd.NetworkLayer.ResponseDTOs.StatusCode statusCode) {
        StatusCode = statusCode;
    }
}
