package com.ets.gd.NetworkLayer.RequestDTOs;


import java.util.List;

public class Equipment  {

    private int ID;
    private String Code;
    private int DeviceTypeID;
    private int ManufacturerID;
    private int ModelID;
    private String SerialNo;
    private String ManufacturerDate;
    private int VendorID;
    private int SizeID;
    private int AgentID;
    private int AssignedLocation;
    private String AssignedLocationCode;
    private int CustomerID;
    private List<InspectionDates> InspectionDates;
    private List<Note> lstFbEquipmentNotes;

    public String getAssignedLocationCode() {
        return AssignedLocationCode;
    }

    public void setAssignedLocationCode(String assignedLocationCode) {
        AssignedLocationCode = assignedLocationCode;
    }

    public int getSizeID() {
        return SizeID;
    }

    public void setSizeID(int sizeID) {
        SizeID = sizeID;
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

    public int getDeviceTypeID() {
        return DeviceTypeID;
    }

    public void setDeviceTypeID(int deviceTypeID) {
        DeviceTypeID = deviceTypeID;
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

    public String getSerialNo() {
        return SerialNo;
    }

    public void setSerialNo(String serialNo) {
        SerialNo = serialNo;
    }

    public String getManufacturerDate() {
        return ManufacturerDate;
    }

    public void setManufacturerDate(String manufacturerDate) {
        ManufacturerDate = manufacturerDate;
    }

    public int getVendorID() {
        return VendorID;
    }

    public void setVendorID(int vendorID) {
        VendorID = vendorID;
    }

    public int getAgentID() {
        return AgentID;
    }

    public void setAgentID(int agentID) {
        AgentID = agentID;
    }

    public int getAssignedLocation() {
        return AssignedLocation;
    }

    public void setAssignedLocation(int assignedLocation) {
        AssignedLocation = assignedLocation;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public List<com.ets.gd.NetworkLayer.RequestDTOs.InspectionDates> getInspectionDates() {
        return InspectionDates;
    }

    public void setInspectionDates(List<com.ets.gd.NetworkLayer.RequestDTOs.InspectionDates> inspectionDates) {
        InspectionDates = inspectionDates;
    }

    public List<Note> getLstFbEquipmentNotes() {
        return lstFbEquipmentNotes;
    }

    public void setLstFbEquipmentNotes(List<Note> lstFbEquipmentNotes) {
        this.lstFbEquipmentNotes = lstFbEquipmentNotes;
    }
}
