package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Customer {

    private int ID;
    private String  Code;
    private String Description;
    private String  ApplicationID;
    private boolean  IsServiceCompany;

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

    public String getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(String applicationID) {
        ApplicationID = applicationID;
    }

    public boolean isServiceCompany() {
        return IsServiceCompany;
    }

    public void setServiceCompany(boolean serviceCompany) {
        IsServiceCompany = serviceCompany;
    }
}
