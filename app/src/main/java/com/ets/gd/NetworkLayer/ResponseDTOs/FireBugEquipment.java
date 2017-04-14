package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class FireBugEquipment  extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Code;
    private String SerialNo;
    private String ManufacturerDate;
    private String InitialFileDate;
    private String LastActivityDate;
    private AgentType AgentType;
    private Customer Customer;
    private DeviceType DeviceType;
    private MyLocation Location;
    private Manufacturer Manufacturer;
    private Rating Rating;
    private Size Size;
    private VendorCode VendorCode;
    private Model Model;
    private int TotalDocuments;
    private int TotalNotes;
    private RealmList<Images> Images;
    private RealmList<MyInspectionDates> InspectionDates;
    private RealmList<InspectionResult> InspectionResults;


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

    public String getInitialFileDate() {
        return InitialFileDate;
    }

    public void setInitialFileDate(String initialFileDate) {
        InitialFileDate = initialFileDate;
    }

    public String getLastActivityDate() {
        return LastActivityDate;
    }

    public void setLastActivityDate(String lastActivityDate) {
        LastActivityDate = lastActivityDate;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.AgentType getAgentType() {
        return AgentType;
    }

    public void setAgentType(com.ets.gd.NetworkLayer.ResponseDTOs.AgentType agentType) {
        AgentType = agentType;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.ets.gd.NetworkLayer.ResponseDTOs.Customer customer) {
        Customer = customer;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType getDeviceType() {
        return DeviceType;
    }

    public void setDeviceType(com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType deviceType) {
        DeviceType = deviceType;
    }

    public MyLocation getLocation() {
        return Location;
    }

    public void setLocation(MyLocation location) {
        Location = location;
    }

    public Manufacturer getManufacturers() {
        return Manufacturer;
    }

    public void setManufacturers(Manufacturer manufacturers) {
        Manufacturer = manufacturers;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Rating getRating() {
        return Rating;
    }

    public void setRating(com.ets.gd.NetworkLayer.ResponseDTOs.Rating rating) {
        Rating = rating;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Size getSize() {
        return Size;
    }

    public void setSize(com.ets.gd.NetworkLayer.ResponseDTOs.Size size) {
        Size = size;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode getVendorCode() {
        return VendorCode;
    }

    public void setVendorCode(com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode vendorCode) {
        VendorCode = vendorCode;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Model getModel() {
        return Model;
    }

    public void setModel(com.ets.gd.NetworkLayer.ResponseDTOs.Model model) {
        Model = model;
    }

    public int getTotalDocuments() {
        return TotalDocuments;
    }

    public void setTotalDocuments(int totalDocuments) {
        TotalDocuments = totalDocuments;
    }

    public int getTotalNotes() {
        return TotalNotes;
    }

    public void setTotalNotes(int totalNotes) {
        TotalNotes = totalNotes;
    }

    public RealmList<com.ets.gd.NetworkLayer.ResponseDTOs.Images> getImages() {
        return Images;
    }

    public void setImages(RealmList<com.ets.gd.NetworkLayer.ResponseDTOs.Images> images) {
        Images = images;
    }

    public RealmList<MyInspectionDates> getInspectionDates() {
        return InspectionDates;
    }

    public void setInspectionDates(RealmList<MyInspectionDates> inspectionDates) {
        InspectionDates = inspectionDates;
    }

    public RealmList<InspectionResult> getInspectionResults() {
        return InspectionResults;
    }

    public void setInspectionResults(RealmList<InspectionResult> inspectionResults) {
        InspectionResults = inspectionResults;
    }
}
