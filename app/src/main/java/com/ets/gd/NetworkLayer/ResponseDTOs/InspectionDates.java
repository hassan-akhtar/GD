package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class InspectionDates {
    private int ID;
    private int EquipmentID;
    private String InspectionType;
    private String DueDate;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public String getInspectionType() {
        return InspectionType;
    }

    public void setInspectionType(String inspectionType) {
        InspectionType = inspectionType;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }
}
