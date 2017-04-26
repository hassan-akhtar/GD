package com.ets.gd.NetworkLayer.RequestDTOs;

public class AddLocation {

    private String Code;
    private String Description;
    private int Customer;
    private int Site;
    private int Building;


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

    public int getCustomer() {
        return Customer;
    }

    public void setCustomer(int customer) {
        Customer = customer;
    }

    public int getSite() {
        return Site;
    }

    public void setSite(int site) {
        Site = site;
    }

    public int getBuilding() {
        return Building;
    }

    public void setBuilding(int building) {
        Building = building;
    }
}
