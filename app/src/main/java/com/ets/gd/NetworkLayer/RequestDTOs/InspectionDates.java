package com.ets.gd.NetworkLayer.RequestDTOs;



public class InspectionDates{

    private int EquipmentID;
    private String InspectionType;
    private String DueDate;

    public InspectionDates() {
    }

    public InspectionDates(int equipmentID, String inspectionType, String dueDate) {
        EquipmentID = equipmentID;
        InspectionType = inspectionType;
        DueDate = dueDate;
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
