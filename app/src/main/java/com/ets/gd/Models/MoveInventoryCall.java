package com.ets.gd.Models;

import java.util.List;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class MoveInventoryCall{

    private String EquipmentCode;
    private String MoveType;
    private String IssueType;
    private int UserID;
    private int JobNumberID;
    private int LocationID;
    private String LocationCode;

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    private List<InventoryMoveCall> Materials;

    public String getEquipmentCode() {
        return EquipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        EquipmentCode = equipmentCode;
    }

    public String getMoveType() {
        return MoveType;
    }

    public void setMoveType(String moveType) {
        MoveType = moveType;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getJobNumberID() {
        return JobNumberID;
    }

    public String getIssueType() {
        return IssueType;
    }

    public void setIssueType(String issueType) {
        IssueType = issueType;
    }

    public void setJobNumberID(int jobNumberID) {
        JobNumberID = jobNumberID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public List<InventoryMoveCall> getMaterials() {
        return Materials;
    }

    public void setMaterials(List<InventoryMoveCall> materials) {
        Materials = materials;
    }
}
