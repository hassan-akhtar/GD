package com.ets.gd.NetworkLayer.ResponseDTOs;

import com.ets.gd.NetworkLayer.RequestDTOs.QuickCount;

import java.util.List;

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
    RealmList<Department> lstDepartments;
    RealmList<ETSLocations> lstETSLocations;
    RealmList<ToolhawkEquipment> lstTHEquipments;
    RealmList<ETSBuilding> lstETSBuildings;
    RealmList<Locations> lstLocations;
    RealmList<QuickCount> lstQuickCount;


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

    public RealmList<Department> getLstDepartments() {
        return lstDepartments;
    }

    public void setLstDepartments(RealmList<Department> lstDepartments) {
        this.lstDepartments = lstDepartments;
    }

    public RealmList<ETSLocations> getLstETSLocations() {
        return lstETSLocations;
    }

    public void setLstETSLocations(RealmList<ETSLocations> lstETSLocations) {
        this.lstETSLocations = lstETSLocations;
    }

    public RealmList<QuickCount> getLstQuickCount() {
        return lstQuickCount;
    }

    public void setLstQuickCount(RealmList<QuickCount> lstQuickCount) {
        this.lstQuickCount = lstQuickCount;
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

    public RealmList<ToolhawkEquipment> getLstTHEquipments() {
        return lstTHEquipments;
    }

    public void setLstTHEquipments(RealmList<ToolhawkEquipment> lstTHEquipments) {
        this.lstTHEquipments = lstTHEquipments;
    }

    public RealmList<ETSBuilding> getLstETSBuildings() {
        return lstETSBuildings;
    }

    public void setLstETSBuildings(RealmList<ETSBuilding> lstETSBuildings) {
        this.lstETSBuildings = lstETSBuildings;
    }
}
