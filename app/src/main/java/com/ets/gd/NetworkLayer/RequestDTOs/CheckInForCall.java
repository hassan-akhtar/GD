package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckInForCall {

    private int UserID;
    private int JobNumberID ;
    private List<String> equipmentCodeList;

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

    public List<String> getEquipmentIDList() {
        return equipmentCodeList;
    }

    public void setEquipmentIDList(List<String> equipmentIDList) {
        this.equipmentCodeList = equipmentIDList;
    }
}
