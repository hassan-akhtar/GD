package com.ets.gd.Models;

import com.ets.gd.NetworkLayer.RequestDTOs.InventoryMoveRealm;

import java.util.List;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class MoveInventoryCall{

    private int EquipmentID;
    private String MoveType;
    private int UserID;
    private int JobNumberID;
    private int LocationID;
    private List<InventoryMoveCall> Materials;

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

    public List<InventoryMoveCall> getMaterials() {
        return Materials;
    }

    public void setMaterials(List<InventoryMoveCall> materials) {
        Materials = materials;
    }
}
