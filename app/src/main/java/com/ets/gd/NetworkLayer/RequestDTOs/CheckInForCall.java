package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Models.CheckInOutEquipment;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckInForCall {

    private int UserID;
    private int JobNumberID ;
    private List<Integer> EquipmentID ;

    public CheckInForCall() {
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

    public List<Integer> getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(List<Integer> equipmentID) {
        EquipmentID = equipmentID;
    }
}
