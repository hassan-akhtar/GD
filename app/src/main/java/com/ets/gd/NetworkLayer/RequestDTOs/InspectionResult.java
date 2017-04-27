package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class InspectionResult {
    private int StatusCodeID;

    private int EquipmentID;
    private String InspectionType;
    private String InspectionDate;
    private int UserId;
    private boolean  Result;

    public InspectionResult(int statusCodeID, int equipmentID, String inspectionType, String inspectionDate, int userId, boolean result) {
        StatusCodeID = statusCodeID;
        EquipmentID = equipmentID;
        InspectionType = inspectionType;
        InspectionDate = inspectionDate;
        UserId = userId;
        Result = result;
    }

    public InspectionResult() {
    }

    public int getStatusCodeID() {
        return StatusCodeID;
    }

    public void setStatusCodeID(int statusCodeID) {
        StatusCodeID = statusCodeID;
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

    public String getInspectionDate() {
        return InspectionDate;
    }

    public void setInspectionDate(String inspectionDate) {
        InspectionDate = inspectionDate;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }
}
