package com.ets.gd.NetworkLayer.RequestDTOs;

import java.util.List;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class MoveInventory extends RealmObject{

    private int EquipmentID;
    private String MoveType;
    private int UserID;
    private int JobNumberID;
    private int LocationID;
    private List<InventoryMove> Materials;

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

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }

    public List<InventoryMove> getMaterials() {
        return Materials;
    }

    public void setMaterials(List<InventoryMove> materials) {
        Materials = materials;
    }
}
