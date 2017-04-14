package com.ets.gd.Models;

import com.ets.gd.NetworkLayer.ResponseDTOs.AgentType;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.RegisteredDevice;
import com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmSyncGetResponseDTO extends RealmObject {

    @PrimaryKey
    private int CustomerId;
    private String DeviceId;
    private String SyncGetTime;
    RealmList<RegisteredDevice> lstDevices;
    RealmList<MobileUser> lstMusers;
    RealmList<FireBugEquipment> lstFbEquipments;
    RealmList<Locations> lstLocations;
    RealmList<DeviceType> lstDeviceType;
    RealmList<Manufacturer> lstManufacturers;
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
