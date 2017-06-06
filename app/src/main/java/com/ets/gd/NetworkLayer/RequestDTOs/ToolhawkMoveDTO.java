package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class ToolhawkMoveDTO {

    private int EquipmentID;
    private String MoveType;
    private int ToEquipmentID;
    private int ToLocationID;
    private int ToJobNumberID;
    private int ConditionID;
    private int UserID;

    public ToolhawkMoveDTO(int equipmentID, String moveType, int toEquipmentID, int toLocationID, int toJobNumberID, int conditionID, int userID) {
        EquipmentID = equipmentID;
        MoveType = moveType;
        ToEquipmentID = toEquipmentID;
        ToLocationID = toLocationID;
        ToJobNumberID = toJobNumberID;
        ConditionID = conditionID;
        UserID = userID;

    }


    public ToolhawkMoveDTO() {

    }

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

    public int getToEquipmentID() {
        return ToEquipmentID;
    }

    public void setToEquipmentID(int toEquipmentID) {
        ToEquipmentID = toEquipmentID;
    }

    public int getToLocationID() {
        return ToLocationID;
    }

    public void setToLocationID(int toLocationID) {
        ToLocationID = toLocationID;
    }

    public int getToJobNumberID() {
        return ToJobNumberID;
    }

    public void setToJobNumberID(int toJobNumberID) {
        ToJobNumberID = toJobNumberID;
    }

    public int getConditionID() {
        return ConditionID;
    }

    public void setConditionID(int conditionID) {
        ConditionID = conditionID;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }
}

