package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/2/2017.
 * General Data
 */

public class ToolhawkTransferDTO extends RealmObject{

    private String EquipmentCode;
    private int DepartmentID;
    private int LocationID;


    public ToolhawkTransferDTO() {
    }
    public ToolhawkTransferDTO(String equipmentID, int departmentID, int locationID) {
        EquipmentCode = equipmentID;
        DepartmentID = departmentID;
        LocationID = locationID;
    }

    public String getEquipmentCode() {
        return EquipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        EquipmentCode = equipmentCode;
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
