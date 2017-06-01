package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class ETSBuilding  extends RealmObject{

    @PrimaryKey
    private int ID;
    private String Code;
    private Site site;
    private Department Department;
    private String Description;

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

    public Site getSite() {
        return site;
    }

    public void setSite(Site site) {
        this.site = site;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Department getDepartment() {
        return Department;
    }

    public void setDepartment(com.ets.gd.NetworkLayer.ResponseDTOs.Department department) {
        Department = department;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }
}
