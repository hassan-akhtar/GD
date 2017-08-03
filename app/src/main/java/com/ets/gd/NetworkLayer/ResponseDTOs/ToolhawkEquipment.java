package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class ToolhawkEquipment extends RealmObject {


    private int ID;
    @PrimaryKey
    private String Code;
    private String SerialNumber;
    private String CurrentState;
    private boolean IsContainer;
    private String PurchaseDate;
    private String UnitCost;
    private String LastServiced;
    private String ServiceDue;
    private String ServiceIncrement;
    private String DueDate;
    private Category Category;
    private Condition Condition;
    private Department Department;
    private ETSLocation ETSLocation;
    private Manufacturer Manufacturer;
    private Model Model;
    private VendorCode VendorCode;
    private EquipmentLocationInfo EquipmentLocationInfo;
    private JobNumber JobNumber;
    private LocationJobNumber LocationJobNumber;
    private Parent Parent;
    private boolean isAdded;
    private boolean isUpdated;
    private RealmList<EquipmentNoteTH> ToolHawkEquipmentNotes;

    public ToolhawkEquipment( int ID, String code, String unitCost, com.ets.gd.NetworkLayer.ResponseDTOs.Department department, com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation ETSLocation, com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer manufacturer, com.ets.gd.NetworkLayer.ResponseDTOs.Model model, boolean isAdded, boolean isUpdated,EquipmentLocationInfo EquipmentLocationInfo) {

        this.ID = ID;
        Code = code;
        UnitCost = unitCost;
        Department = department;
        this.ETSLocation = ETSLocation;
        Manufacturer = manufacturer;
        Model = model;
        this.isAdded = isAdded;
        this.isUpdated = isUpdated;
        this.EquipmentLocationInfo = EquipmentLocationInfo;
    }

    public ToolhawkEquipment() {

    }

    public RealmList<EquipmentNoteTH> getToolHawkEquipmentNotes() {
        return ToolHawkEquipmentNotes;
    }

    public void setToolHawkEquipmentNotes(RealmList<EquipmentNoteTH> toolHawkEquipmentNotes) {
        ToolHawkEquipmentNotes = toolHawkEquipmentNotes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public String getCode() {
        return Code;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getSerialNumber() {
        return SerialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        SerialNumber = serialNumber;
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

    public String getPurchaseDate() {
        return PurchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        PurchaseDate = purchaseDate;
    }

    public String getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(String unitCost) {
        UnitCost = unitCost;
    }

    public String getLastServiced() {
        return LastServiced;
    }

    public void setLastServiced(String lastServiced) {
        LastServiced = lastServiced;
    }

    public String getServiceDue() {
        return ServiceDue;
    }

    public void setServiceDue(String serviceDue) {
        ServiceDue = serviceDue;
    }

    public String getServiceIncrement() {
        return ServiceIncrement;
    }

    public void setServiceIncrement(String serviceIncrement) {
        ServiceIncrement = serviceIncrement;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Category getCategory() {
        return Category;
    }

    public void setCategory(com.ets.gd.NetworkLayer.ResponseDTOs.Category category) {
        Category = category;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Condition getCondition() {
        return Condition;
    }

    public void setCondition(com.ets.gd.NetworkLayer.ResponseDTOs.Condition condition) {
        Condition = condition;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Department getDepartment() {
        return Department;
    }

    public void setDepartment(com.ets.gd.NetworkLayer.ResponseDTOs.Department department) {
        Department = department;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation getETSLocation() {
        return ETSLocation;
    }

    public void setETSLocation(com.ets.gd.NetworkLayer.ResponseDTOs.ETSLocation ETSLocation) {
        this.ETSLocation = ETSLocation;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public void setManufacturer(com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer manufacturer) {
        Manufacturer = manufacturer;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Model getModel() {
        return Model;
    }

    public void setModel(com.ets.gd.NetworkLayer.ResponseDTOs.Model model) {
        Model = model;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode getVendorCode() {
        return VendorCode;
    }

    public void setVendorCode(com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode vendorCode) {
        VendorCode = vendorCode;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentLocationInfo getEquipmentLocationInfo() {
        return EquipmentLocationInfo;
    }

    public void setEquipmentLocationInfo(com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentLocationInfo equipmentLocationInfo) {
        EquipmentLocationInfo = equipmentLocationInfo;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(com.ets.gd.NetworkLayer.ResponseDTOs.JobNumber jobNumber) {
        JobNumber = jobNumber;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.LocationJobNumber getLocationJobNumber() {
        return LocationJobNumber;
    }

    public void setLocationJobNumber(com.ets.gd.NetworkLayer.ResponseDTOs.LocationJobNumber locationJobNumber) {
        LocationJobNumber = locationJobNumber;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Parent getParent() {
        return Parent;
    }

    public void setParent(com.ets.gd.NetworkLayer.ResponseDTOs.Parent parent) {
        Parent = parent;
    }
}
