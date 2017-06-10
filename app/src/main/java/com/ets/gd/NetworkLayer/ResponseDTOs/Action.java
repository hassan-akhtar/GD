package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

public class Action extends RealmObject {

    private int ID;
    private String Code;
    private String Description;
    private String ModifiedBy;
    private String ModifiedTime;

    public Action(int ID, String code, String description, String modifiedBy, String modifiedTime) {
        this.ID = ID;
        Code = code;
        Description = description;
        ModifiedBy = modifiedBy;
        ModifiedTime = modifiedTime;
    }

    public Action() {

    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
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
}
