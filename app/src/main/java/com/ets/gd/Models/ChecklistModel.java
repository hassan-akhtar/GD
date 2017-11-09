package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 11/9/2017.
 * General Data
 */

public class ChecklistModel extends RealmObject{

    int ID;
    String Code;
    int DeviceTypeID;
    String CheckList;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(int deviceTypeID) {
        DeviceTypeID = deviceTypeID;
    }

    public String getCheckList() {
        return CheckList;
    }

    public void setCheckList(String checkList) {
        CheckList = checkList;
    }
}
