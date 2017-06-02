package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/2/2017.
 * General Data
 */

public class TransferToolhawk  extends RealmObject{

    private int EquipmentID;
    private int DepartmentID;
    private int LocationID;


    public TransferToolhawk() {
    }
    public TransferToolhawk(int equipmentID, int departmentID, int locationID) {
        EquipmentID = equipmentID;
        DepartmentID = departmentID;
        LocationID = locationID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }
}
