package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class THEquipment {

    private int ID;
    private String Code;
    private int DepartmentID;
    private int LocationID;
    private int ManufacturerID;
    private int ModelID;
    private String UnitCost;


    public THEquipment(int ID, String code, int departmentID, int locationID, int manufacturerID, int modelID, String unitCost) {
        this.ID = ID;
        Code = code;
        DepartmentID = departmentID;
        LocationID = locationID;
        ManufacturerID = manufacturerID;
        ModelID = modelID;
        UnitCost = unitCost;
    }

    public THEquipment() {
    }

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

    public int getManufacturerID() {
        return ManufacturerID;
    }

    public void setManufacturerID(int manufacturerID) {
        ManufacturerID = manufacturerID;
    }

    public int getModelID() {
        return ModelID;
    }

    public void setModelID(int modelID) {
        ModelID = modelID;
    }

    public String getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(String unitCost) {
        UnitCost = unitCost;
    }
}
