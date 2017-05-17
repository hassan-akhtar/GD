package com.ets.gd.Models;

import com.ets.gd.NetworkLayer.ResponseDTOs.AgentType;
import com.ets.gd.NetworkLayer.ResponseDTOs.AllCustomers;
import com.ets.gd.NetworkLayer.ResponseDTOs.DeviceType;
import com.ets.gd.NetworkLayer.ResponseDTOs.EquipmentNote;
import com.ets.gd.NetworkLayer.ResponseDTOs.FireBugEquipment;
import com.ets.gd.NetworkLayer.ResponseDTOs.Locations;
import com.ets.gd.NetworkLayer.ResponseDTOs.Manufacturer;
import com.ets.gd.NetworkLayer.ResponseDTOs.MobileUser;
import com.ets.gd.NetworkLayer.ResponseDTOs.Model;
import com.ets.gd.NetworkLayer.ResponseDTOs.RegisteredDevice;
import com.ets.gd.NetworkLayer.ResponseDTOs.Routes;
import com.ets.gd.NetworkLayer.ResponseDTOs.SyncCustomer;
import com.ets.gd.NetworkLayer.ResponseDTOs.VendorCode;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class RealmSyncGetResponseDTO extends RealmObject {

    @PrimaryKey
    int ID;
    RealmList<AllCustomers> lstCustomers;
    RealmList<SyncCustomer> lstCustomerData;
    RealmList<DeviceType> lstDeviceType;
    RealmList<Manufacturer> lstManufacturers;
    RealmList<Model> lstModels;
    RealmList<VendorCode> lstVendorCodes;
    RealmList<AgentType> lstAgentTypes;
    RealmList<RegisteredDevice> lstDevices;
    RealmList<EquipmentNote> lstFbEquipmentNotes;
    RealmList<Routes> lstRoutes;


    public RealmList<DeviceType> getLstDeviceType() {
        return lstDeviceType;
    }

    public void setLstDeviceType(RealmList<DeviceType> lstDeviceType) {
        this.lstDeviceType = lstDeviceType;
    }

    public RealmList<EquipmentNote> getLstFbEquipmentNotes() {
        return lstFbEquipmentNotes;
    }

    public void setLstFbEquipmentNotes(RealmList<EquipmentNote> lstFbEquipmentNotes) {
        this.lstFbEquipmentNotes = lstFbEquipmentNotes;
    }

    public RealmList<Routes> getLstRoutes() {
        return lstRoutes;
    }

    public void setLstRoutes(RealmList<Routes> lstRoutes) {
        this.lstRoutes = lstRoutes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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

    public RealmList<AllCustomers> getLstCustomers() {
        return lstCustomers;
    }

    public void setLstCustomers(RealmList<AllCustomers> lstCustomers) {
        this.lstCustomers = lstCustomers;
    }

    public RealmList<SyncCustomer> getLstCustomerData() {
        return lstCustomerData;
    }

    public void setLstCustomerData(RealmList<SyncCustomer> lstCustomerData) {
        this.lstCustomerData = lstCustomerData;
    }

    public RealmList<RegisteredDevice> getLstDevices() {
        return lstDevices;
    }

    public void setLstDevices(RealmList<RegisteredDevice> lstDevices) {
        this.lstDevices = lstDevices;
    }


}