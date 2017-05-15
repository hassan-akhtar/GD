package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UnitinspectionResult extends RealmObject {

    private int EquipmentID;
    private String InspectionType;
    private String InspectionDate;
    private int UserId;
    private boolean  Result;
    private int NewLocationID;
    private String ReplaceType;
    private RealmList<InspectionStatusCodes> InspectionStatusCodes;

    public UnitinspectionResult(int equipmentID, String inspectionType, String inspectionDate, int userId, boolean result, RealmList<com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes> inspectionStatusCodes, int NewLocationID, String ReplaceType) {
        EquipmentID = equipmentID;
        InspectionType = inspectionType;
        InspectionDate = inspectionDate;
        UserId = userId;
        Result = result;
        InspectionStatusCodes = inspectionStatusCodes;
        this.NewLocationID = NewLocationID;
        this.ReplaceType = ReplaceType;
    }

    public UnitinspectionResult() {
    }

    public RealmList<com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes> getInspectionStatusCodes() {
        return InspectionStatusCodes;
    }

    public void setInspectionStatusCodes(RealmList<com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes> inspectionStatusCodes) {
        InspectionStatusCodes = inspectionStatusCodes;
    }

    public int getNewLocationID() {
        return NewLocationID;
    }

    public void setNewLocationID(int newLocationID) {
        NewLocationID = newLocationID;
    }

    public String getReplaceType() {
        return ReplaceType;
    }

    public void setReplaceType(String replaceType) {
        ReplaceType = replaceType;
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
