package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class TransferToolhawk {
    private String EquipmentCode;
    private int DepartmentID;
    private int LocationID;


    public TransferToolhawk() {
    }
    public TransferToolhawk(String equipmentID, int departmentID, int locationID) {
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

