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
    public TransferToolhawk(String EqCode, int departmentID, int locationID) {
        EquipmentCode = EqCode;
        DepartmentID = departmentID;
        LocationID = locationID;
    }

    public String getEquipmentID() {
        return EquipmentCode;
    }

    public void setEquipmentID(String EqCode) {
        EquipmentCode = EqCode;
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

