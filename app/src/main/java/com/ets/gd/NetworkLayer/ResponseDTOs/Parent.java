package com.ets.gd.NetworkLayer.ResponseDTOs;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class Parent extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Code;
    private int CategoryID;
    private int ManufacturerID;
    private int ModelID;
    private String SerialNumber;
    private int VendorID;
    private int ConditionID;
    private int DepartmentID;
    private int LocationID;
    private String CurrentState;
    private boolean IsContainer;
    private int JobNumberID;

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

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
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

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    public int getConditionID() {
        return ConditionID;
    }

    public void setConditionID(int conditionID) {
        ConditionID = conditionID;
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

    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }

    public boolean isContainer() {
        return IsContainer;
    }

    public void setContainer(boolean container) {
        IsContainer = container;
    }

    public int getJobNumberID() {
        return JobNumberID;
    }

    public void setJobNumberID(int jobNumberID) {
        JobNumberID = jobNumberID;
    }
}
