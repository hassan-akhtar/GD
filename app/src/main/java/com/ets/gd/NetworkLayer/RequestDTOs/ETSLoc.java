package com.ets.gd.NetworkLayer.RequestDTOs;


public class ETSLoc  {
    private int ID;
    private String Code;
    private String Description;
    private int CustomerID;
    private int SiteID;
    private int BuildingID;


    public ETSLoc(int ID, String code, String description, int customerID, int siteID, int buildingID) {
        this.ID = ID;
        Code = code;
        Description = description;
        CustomerID = customerID;
        SiteID = siteID;
        BuildingID = buildingID;
    }


    public ETSLoc() {

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

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getSiteID() {
        return SiteID;
    }

    public void setSiteID(int siteID) {
        SiteID = siteID;
    }

    public int getBuildingID() {
        return BuildingID;
    }

    public void setBuildingID(int buildingID) {
        BuildingID = buildingID;
    }


}

