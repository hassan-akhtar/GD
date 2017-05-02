package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class InspectionStatusCodes extends RealmObject {

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
