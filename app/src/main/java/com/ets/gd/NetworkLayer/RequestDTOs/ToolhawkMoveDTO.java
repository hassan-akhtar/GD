package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class ToolhawkMoveDTO {

    private String EquipmentCode;
    private String MoveType;
    private String ToEquipmentCode;
    private int ToLocationID;
    private String ToLocationCode;
    private int ToJobNumberID;
    private int ConditionID;
    private int UserID;

    public ToolhawkMoveDTO(String EqCode, String moveType, String toEquipmentID, int toLocationID, int toJobNumberID, int conditionID, int userID) {
        EquipmentCode = EqCode;
        MoveType = moveType;
        ToEquipmentCode = toEquipmentID;
        ToLocationID = toLocationID;
        ToJobNumberID = toJobNumberID;
        ConditionID = conditionID;
        UserID = userID;

    }

    public String getToLocationCode() {
        return ToLocationCode;
    }

    public void setToLocationCode(String toLocationCode) {
        ToLocationCode = toLocationCode;
    }

    public ToolhawkMoveDTO() {

    }

    public String getEquipmentID() {
        return EquipmentCode;
    }

    public void setEquipmentID(String EqCode) {
        EquipmentCode = EqCode;
    }

    public String getMoveType() {
        return MoveType;
    }

    public void setMoveType(String moveType) {
        MoveType = moveType;
    }

    public String getToEquipmentID() {
        return ToEquipmentCode;
    }

    public void setToEquipmentID(String toEquipmentID) {
        ToEquipmentCode = toEquipmentID;
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

