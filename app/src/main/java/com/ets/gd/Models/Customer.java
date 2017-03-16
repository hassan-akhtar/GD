package com.ets.gd.Models;

public class Customer {
    private String getApplicationID() {
        return ApplicationID;
    }

    public void setApplicationID(String applicationID) {
        ApplicationID = applicationID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    private String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    private String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private String getIsServiceCompany() {
        return IsServiceCompany;
    }

    public void setIsServiceCompany(String isServiceCompany) {
        IsServiceCompany = isServiceCompany;
    }

    private String ID;
    private String Code;
    private String Description;
    private String ApplicationID;
    private String IsServiceCompany;

    public Customer() {
    }

    public String toString()
    {
        return "ID : "+getID()+"\n"+
                "Code : "+getCode()+"\n"+
                "Description : "+getDescription()+"\n"+
                "ApplicationID : "+getApplicationID()+"\n"+
                "isServiceCompany : "+getIsServiceCompany();
    }

}
