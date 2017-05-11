package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SyncCustomer extends RealmObject {
    @PrimaryKey
    private int CustomerId;
    private String DeviceId;
    private String SyncGetTime;
    private boolean IsServiceCompany;
    RealmList<MobileUser> lstMusers;
    RealmList<FireBugEquipment> lstFbEquipments;
    RealmList<Locations> lstLocations;

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

    public RealmList<Locations> getLstLocations() {
        return lstLocations;
    }

    public void setLstLocations(RealmList<Locations> lstLocations) {
        this.lstLocations = lstLocations;
    }

    public boolean isServiceCompany() {
        return IsServiceCompany;
    }

    public void setServiceCompany(boolean serviceCompany) {
        IsServiceCompany = serviceCompany;
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

}
