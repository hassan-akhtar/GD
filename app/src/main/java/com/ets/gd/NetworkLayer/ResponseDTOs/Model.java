package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Model {

    private int ID;
    private String Code;
    private String UnitCost;
    private String Description;
    private int ManufacturerId;
    private String CompanyName;
    private String DataSheetUrl;
    private String ModifiedBy;
    private String ModifiedTime;

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

    public String getUnitCost() {
        return UnitCost;
    }

    public void setUnitCost(String unitCost) {
        UnitCost = unitCost;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getManufacturerId() {
        return ManufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        ManufacturerId = manufacturerId;
    }

    public String getCompanyName() {
        return CompanyName;
    }

    public void setCompanyName(String companyName) {
        CompanyName = companyName;
    }

    public String getDataSheetUrl() {
        return DataSheetUrl;
    }

    public void setDataSheetUrl(String dataSheetUrl) {
        DataSheetUrl = dataSheetUrl;
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
