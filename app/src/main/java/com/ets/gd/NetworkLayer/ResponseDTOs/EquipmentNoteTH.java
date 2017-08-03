package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 4/21/2017.
 * General Data
 */

public class EquipmentNoteTH extends RealmObject {


    private int ID;
    private String EquipmentCode;
    private int EquipmentID;
    private String Note;
    private String ModifiedBy;
    private String ModifiedTime;


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getModifiedBy() {
        return ModifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        ModifiedBy = modifiedBy;
    }

    public String getModifiedTime() {
        return ModifiedTime;
    }

    public void setModifiedTime(String modifiedTime) {
        ModifiedTime = modifiedTime;
    }

    public String getEquipmentCode() {
        return EquipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        EquipmentCode = equipmentCode;
    }
}
