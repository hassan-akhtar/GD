package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;

public class UnitinspectionResult extends RealmObject {

    private String EquipmentCode;
    private String InspectionType;
    private String InspectionDate;
    private Boolean isReplaced;
    private int UserId;
    private int RouteID;
    private boolean  Result;
    private int NewLocationID;
    private String NewEquipmentCode;
    private String ReplaceType;
    private int routeAssetID;
    private boolean isScanned;
    private boolean isCompleted;
    private RealmList<InspectionStatusCodes> InspectionStatusCodes;

    public UnitinspectionResult(String equipmentID, String inspectionType, String inspectionDate, int userId, boolean result, RealmList<com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes> inspectionStatusCodes, int NewLocationID, String ReplaceType) {
        EquipmentCode = equipmentID;
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

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getRouteAssetID() {
        return routeAssetID;
    }

    public void setRouteAssetID(int routeAssetID) {
        this.routeAssetID = routeAssetID;
    }

    public RealmList<com.ets.gd.NetworkLayer.RequestDTOs.InspectionStatusCodes> getInspectionStatusCodes() {
        return InspectionStatusCodes;
    }

    public int getRouteID() {
        return RouteID;
    }

    public void setRouteID(int routeID) {
        RouteID = routeID;
    }

    public Boolean getReplaced() {
        return isReplaced;
    }

    public void setReplaced(Boolean replaced) {
        isReplaced = replaced;
    }

    public String getNewEquipmentCode() {
        return NewEquipmentCode;
    }

    public void setNewEquipmentCode(String newEquipmentCode) {
        NewEquipmentCode = newEquipmentCode;
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

    public String getEquipmentCode() {
        return EquipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        EquipmentCode = equipmentCode;
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
