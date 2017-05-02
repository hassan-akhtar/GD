package com.ets.gd.NetworkLayer.ResponseDTOs;


import io.realm.RealmList;
import io.realm.annotations.PrimaryKey;


public class SyncGetResponseDTO extends ResponseDTO {

    @PrimaryKey
    private int CustomerId;
    private String DeviceId;
    private String SyncGetTime;
    private boolean IsServiceCompany;
    RealmList<AllCustomers> lstCustomers;
    RealmList<RegisteredDevice> lstDevices;
    RealmList<MobileUser> lstMusers;
    RealmList<FireBugEquipment> lstFbEquipments;
    RealmList<Locations> lstLocations;
    RealmList<DeviceType> lstDeviceType;
    RealmList<Manufacturer> lstManufacturers;
    RealmList<EquipmentNote> lstFbEquipmentNotes;
    RealmList<Model> lstModels;
    RealmList<VendorCode> lstVendorCodes;
    RealmList<AgentType> lstAgentTypes;

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int customerId) {
        CustomerId = customerId;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public void setDeviceId(String deviceId) {
        DeviceId = deviceId;
    }

    public String getSyncGetTime() {
        return SyncGetTime;
    }

    public void setSyncGetTime(String syncGetTime) {
        SyncGetTime = syncGetTime;
    }

    public RealmList<RegisteredDevice> getLstDevices() {
        return lstDevices;
    }

    public void setLstDevices(RealmList<RegisteredDevice> lstDevices) {
        this.lstDevices = lstDevices;
    }

    public RealmList<AllCustomers> getLstCustomers() {
        return lstCustomers;
    }

    public boolean isServiceCompany() {
        return IsServiceCompany;
    }

    public void setServiceCompany(boolean serviceCompany) {
        IsServiceCompany = serviceCompany;
    }

    public void setLstCustomers(RealmList<AllCustomers> lstCustomers) {
        this.lstCustomers = lstCustomers;
    }

    public RealmList<EquipmentNote> getLstFbEquipmentNotes() {
        return lstFbEquipmentNotes;
    }

    public void setLstFbEquipmentNotes(RealmList<EquipmentNote> lstFbEquipmentNotes) {
        this.lstFbEquipmentNotes = lstFbEquipmentNotes;
    }

    public RealmList<MobileUser> getLstMusers() {
        return lstMusers;
    }

    public void setLstMusers(RealmList<MobileUser> lstMusers) {
        this.lstMusers = lstMusers;
    }

    public RealmList<FireBugEquipment> getLstFbEquipments() {
        return lstFbEquipments;
    }

    public void setLstFbEquipments(RealmList<FireBugEquipment> lstFbEquipments) {
        this.lstFbEquipments = lstFbEquipments;
    }

    public RealmList<Locations> getLstLocations() {
        return lstLocations;
    }

    public void setLstLocations(RealmList<Locations> lstLocations) {
        this.lstLocations = lstLocations;
    }

    public RealmList<DeviceType> getLstDeviceType() {
        return lstDeviceType;
    }

    public void setLstDeviceType(RealmList<DeviceType> lstDeviceType) {
        this.lstDeviceType = lstDeviceType;
    }

    public RealmList<Manufacturer> getLstManufacturers() {
        return lstManufacturers;
    }

    public void setLstManufacturers(RealmList<Manufacturer> lstManufacturers) {
        this.lstManufacturers = lstManufacturers;
    }

    public RealmList<Model> getLstModels() {
        return lstModels;
    }

    public void setLstModels(RealmList<Model> lstModels) {
        this.lstModels = lstModels;
    }

    public RealmList<VendorCode> getLstVendorCodes() {
        return lstVendorCodes;
    }

    public void setLstVendorCodes(RealmList<VendorCode> lstVendorCodes) {
        this.lstVendorCodes = lstVendorCodes;
    }

    public RealmList<AgentType> getLstAgentTypes() {
        return lstAgentTypes;
    }

    public void setLstAgentTypes(RealmList<AgentType> lstAgentTypes) {
        this.lstAgentTypes = lstAgentTypes;
    }
}
