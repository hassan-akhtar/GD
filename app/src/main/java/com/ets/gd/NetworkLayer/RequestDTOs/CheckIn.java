package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckIn extends RealmObject {

    private int UserID;
    private int JobNumberID ;
    private List<Integer> EquipmentID ;

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

    public List<Integer> getLstEquipments() {
        return EquipmentID ;
    }

    public void setLstEquipments(List<Integer> lstEquipments) {
        this.EquipmentID  = lstEquipments;
    }
}
