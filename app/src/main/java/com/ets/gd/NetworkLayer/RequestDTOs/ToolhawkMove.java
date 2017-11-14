package com.ets.gd.NetworkLayer.RequestDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/6/2017.
 * General Data
 */

public class ToolhawkMove extends RealmObject{

    private  String EquipmentCode;
    private  String  MoveType;
    private  String ToEquipmentCode;
    private  int ToLocationID;
    private  String ToLocationCode;
    private  int ToJobNumberID;
    private  int ConditionID;
    private  int UserID;

    public ToolhawkMove(String equipmentID, String moveType, String toEquipmentID, int toLocationID, int toJobNumberID, int conditionID, int userID) {
        EquipmentCode = equipmentID;
        MoveType = moveType;
        ToEquipmentCode = toEquipmentID;
        ToLocationID = toLocationID;
        ToJobNumberID = toJobNumberID;
        ConditionID = conditionID;
        UserID = userID;

    }


    public ToolhawkMove() {

    }

    public String getToLocationCode() {
        return ToLocationCode;
    }

    public void setToLocationCode(String toLocationCode) {
        ToLocationCode = toLocationCode;
    }

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

    public String getToEquipmentCode() {
        return ToEquipmentCode;
    }

    public void setToEquipmentCode(String toEquipmentCode) {
        ToEquipmentCode = toEquipmentCode;
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
