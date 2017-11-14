package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Models.CheckInOutEquipment;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckIn extends RealmObject {

    private int UserID;
    private int JobNumberID ;
    private RealmList<CheckInOutEquipment> EquipmentCodes;

    public CheckIn() {
    }


    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getJobNumberID() {
        return JobNumberID ;
    }

    public void setJobNumberID(int jobNumberID) {
        this.JobNumberID  = jobNumberID;
    }

    public RealmList<CheckInOutEquipment> getEquipmentCodes() {
        return EquipmentCodes;
    }

    public void setEquipmentCodes(RealmList<CheckInOutEquipment> equipmentCodes) {
        EquipmentCodes = equipmentCodes;
    }
}
