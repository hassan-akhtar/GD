package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckOutForCall  {

    private String CheckOutType;
    private int UserID;
    private int JobNumberID;
    private String DueDate;
    private List<Integer> equipmentIDList;

    public CheckOutForCall() {
    }

    public String getCheckOutType() {
        return CheckOutType;
    }

    public void setCheckOutType(String checkOutType) {
        CheckOutType = checkOutType;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getJobNumberID() {
        return JobNumberID;
    }

    public void setJobNumberID(int jobNumberID) {
        JobNumberID = jobNumberID;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public List<Integer> getEquipmentIDList() {
        return equipmentIDList;
    }

    public void setEquipmentIDList(List<Integer> equipmentIDList) {
        this.equipmentIDList = equipmentIDList;
    }
}
