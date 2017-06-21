package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class MoveInventoryRealm extends RealmObject{

    private int EquipmentID;
    private String MoveType;
    private String IssueType;
    private int UserID;
    private int JobNumberID;
    private int LocationID;
    boolean isIssued;
    boolean isMoved;
    boolean isReceived;
    private RealmList<InventoryMoveRealm> Materials;

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
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

    public void setJobNumberID(int jobNumberID) {
        JobNumberID = jobNumberID;
    }

    public boolean issued() {
        return isIssued;
    }

    public void setIssued(boolean issued) {
        isIssued = issued;
    }

    public boolean isMoved() {
        return isMoved;
    }

    public void setMoved(boolean moved) {
        isMoved = moved;
    }

    public boolean isReceived() {
        return isReceived;
    }

    public void setReceived(boolean received) {
        isReceived = received;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public RealmList<InventoryMoveRealm> getMaterials() {
        return Materials;
    }

    public String getIssueType() {
        return IssueType;
    }

    public void setIssueType(String issueType) {
        IssueType = issueType;
    }

    public void setMaterials(RealmList<InventoryMoveRealm> materials) {
        Materials = materials;
    }
}
