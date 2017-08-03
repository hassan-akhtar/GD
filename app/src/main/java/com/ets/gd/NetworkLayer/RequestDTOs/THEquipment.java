package com.ets.gd.NetworkLayer.RequestDTOs;

import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNoteTH;

import java.util.List;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class THEquipment {

    private int ID;
    private String Code;
    private int DepartmentID;
    private int LocationID;
    private String LocationCode;
    private int ManufacturerID;
    private int ModelID;
    private float UnitCost;
    private List<EquipmentNoteTH> ToolHawkEquipmentNotes;


    public THEquipment(int ID, String code, int departmentID, int locationID, int manufacturerID, int modelID, float unitCost) {
        this.ID = ID;
        Code = code;
        DepartmentID = departmentID;
        LocationID = locationID;
        ManufacturerID = manufacturerID;
        ModelID = modelID;
        UnitCost = unitCost;
    }

    public List<EquipmentNoteTH> getToolHawkEquipmentNotes() {
        return ToolHawkEquipmentNotes;
    }

    public void setToolHawkEquipmentNotes(List<EquipmentNoteTH> toolHawkEquipmentNotes) {
        ToolHawkEquipmentNotes = toolHawkEquipmentNotes;
    }

    public THEquipment() {
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
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

    public float getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(float unitCost) {
        UnitCost = unitCost;
    }
}
