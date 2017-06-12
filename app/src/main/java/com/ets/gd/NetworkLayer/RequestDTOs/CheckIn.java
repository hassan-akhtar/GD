package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Models.CheckInOutEquipment;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckIn extends RealmObject {

    private int UserID;
    private int JobNumberID ;
    private RealmList<CheckInOutEquipment> EquipmentID ;

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

    public RealmList<CheckInOutEquipment> getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(RealmList<CheckInOutEquipment> equipmentID) {
        EquipmentID = equipmentID;
    }
}
