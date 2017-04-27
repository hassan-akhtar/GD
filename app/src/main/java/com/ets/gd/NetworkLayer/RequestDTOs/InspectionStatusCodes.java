package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class InspectionStatusCodes {

    private int StatusCodeID;

    public InspectionStatusCodes(int statusCodeID) {
        StatusCodeID = statusCodeID;
    }

    public InspectionStatusCodes() {
    }

    public int getStatusCodeID() {
        return StatusCodeID;
    }

    public void setStatusCodeID(int statusCodeID) {
        StatusCodeID = statusCodeID;
    }
}
