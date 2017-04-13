package com.ets.gd.NetworkLayer.ResponseDTOs;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Location {


    private int ID;
    private String Code;
    private String Description;
    private String Floor;
    private String Room;
    private int Customer;
    private int Site;
    private int Building;
    private int locationTypeVM;


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

    public String getFloor() {
        return Floor;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public String getRoom() {
        return Room;
    }

    public void setRoom(String room) {
        Room = room;
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

    public int getLocationTypeVM() {
        return locationTypeVM;
    }

    public void setLocationTypeVM(int locationTypeVM) {
        this.locationTypeVM = locationTypeVM;
    }
}
