package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

public class EquipmentMaintenance  extends RealmObject{

    private int ID;
    private String EquipmentCode;
    private int MaintenanceCategoryID;
    private int MaintenanceActionID;
    private String Cost;
    private String MaintenanceDate;
    private boolean UpdateLastServiceDate;
    private String Note;

    public EquipmentMaintenance() {
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEquipmentID() {
        return EquipmentCode;
    }

    public void setEquipmentID(String equipmentID) {
        EquipmentCode = equipmentID;
    }

    public int getMaintenanceCategoryID() {
        return MaintenanceCategoryID;
    }

    public void setMaintenanceCategoryID(int maintenanceCategoryID) {
        MaintenanceCategoryID = maintenanceCategoryID;
    }

    public int getMaintenanceActionID() {
        return MaintenanceActionID;
    }

    public void setMaintenanceActionID(int maintenanceActionID) {
        MaintenanceActionID = maintenanceActionID;
    }

    public String getCost() {
        return Cost;
    }

    public void setCost(String cost) {
        Cost = cost;
    }

    public String getMaintenanceDate() {
        return MaintenanceDate;
    }

    public void setMaintenanceDate(String maintenanceDate) {
        MaintenanceDate = maintenanceDate;
    }

    public boolean isUpdateLastServiceDate() {
        return UpdateLastServiceDate;
    }

    public void setUpdateLastServiceDate(boolean updateLastServiceDate) {
        UpdateLastServiceDate = updateLastServiceDate;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
