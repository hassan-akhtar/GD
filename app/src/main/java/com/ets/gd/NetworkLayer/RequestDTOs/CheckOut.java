package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.Models.CheckInOutEquipment;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/11/2017.
 * General Data
 */

public class CheckOut extends RealmObject {

    private String CheckOutType;
    private int UserID;
    private int JobNumberID;
    private String DueDate;
    private RealmList<CheckInOutEquipment> EquipmentCodes;

    public CheckOut() {
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

    public RealmList<CheckInOutEquipment> getEquipmentCodes() {
        return EquipmentCodes;
    }

    public void setEquipmentCodes(RealmList<CheckInOutEquipment> equipmentCodes) {
        EquipmentCodes = equipmentCodes;
    }
}
