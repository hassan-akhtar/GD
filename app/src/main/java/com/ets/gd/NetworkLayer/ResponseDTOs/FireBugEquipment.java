package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class FireBugEquipment  extends RealmObject {

    private int ID;
    @PrimaryKey

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
    private boolean isUpdated;
    private boolean isAdded;
    private boolean isMoved;
    private boolean isTransferred;
    private boolean isUnitInspected;
    private boolean isRouteUnitInspected;
    private Size Size;
    private VendorCode VendorCode;
    private Model Model;
    private int TotalDocuments;
    private int TotalNotes;
    private RealmList<Images> Images;
    private RealmList<MyInspectionDates> InspectionDates;
    private RealmList<InspectionResult> InspectionResults;

    public FireBugEquipment(String code, String serialNo, String manufacturerDate, com.ets.gd.NetworkLayer.ResponseDTOs.AgentType agentType, com.ets.gd.NetworkLayer.ResponseDTOs.Customer customer, com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType deviceType, MyLocation location, com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer manufacturer, com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode vendorCode, com.ets.gd.NetworkLayer.ResponseDTOs.Model model) {
        Code = code;
        SerialNo = serialNo;
        ManufacturerDate = manufacturerDate;
        AgentType = agentType;
        Customer = customer;
        DeviceType = deviceType;
        Location = location;
        Manufacturer = manufacturer;
        VendorCode = vendorCode;
        Model = model;
    }

    public FireBugEquipment(int ID, String code, String serialNo, String manufacturerDate, com.ets.gd.NetworkLayer.ResponseDTOs.AgentType agentType, com.ets.gd.NetworkLayer.ResponseDTOs.Customer customer, com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType deviceType, MyLocation location, com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer manufacturer, com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode vendorCode, com.ets.gd.NetworkLayer.ResponseDTOs.Model model, boolean isUpdated, boolean isAdded) {
        this.ID = ID;
        Code = code;
        SerialNo = serialNo;
        ManufacturerDate = manufacturerDate;
        AgentType = agentType;
        Customer = customer;
        DeviceType = deviceType;
        Location = location;
        Manufacturer = manufacturer;
        VendorCode = vendorCode;
        Model = model;
        this.isUpdated = isUpdated;
        this.isAdded = isAdded;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer getManufacturer() {
        return Manufacturer;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public boolean isRouteUnitInspected() {
        return isRouteUnitInspected;
    }

    public void setRouteUnitInspected(boolean routeUnitInspected) {
        isRouteUnitInspected = routeUnitInspected;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public boolean isTransferred() {
        return isTransferred;
    }

    public void setTransferred(boolean transferred) {
        isTransferred = transferred;
    }

    public void setManufacturer(com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer manufacturer) {
        Manufacturer = manufacturer;
    }

    public boolean isUpdated() {
        return isUpdated;
    }

    public void setUpdated(boolean updated) {
        isUpdated = updated;
    }

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
    }

    public FireBugEquipment(){}

    public boolean isUnitInspected() {
        return isUnitInspected;
    }

    public void setUnitInspected(boolean unitInspected) {
        isUnitInspected = unitInspected;
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
