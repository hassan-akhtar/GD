package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class ETSLocations extends RealmObject {

    private int ID;
    @PrimaryKey
    private String Code;
    private String Description;
    private int DepartmentID;
    private String Floor;
    private String Room;
    private Customer Customer;
    private boolean isActive;
    private Site Site;
    private Building Building;
    boolean isAdded ;

    public boolean isAdded() {
        return isAdded;
    }

    public void setAdded(boolean added) {
        isAdded = added;
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

    public int getDepartmentID() {
        return DepartmentID;
    }

    public void setDepartmentID(int departmentID) {
        DepartmentID = departmentID;
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

    public com.ets.gd.NetworkLayer.ResponseDTOs.Customer getCustomer() {
        return Customer;
    }

    public void setCustomer(com.ets.gd.NetworkLayer.ResponseDTOs.Customer customer) {
        Customer = customer;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Site getSite() {
        return Site;
    }

    public void setSite(com.ets.gd.NetworkLayer.ResponseDTOs.Site site) {
        Site = site;
    }

    public com.ets.gd.NetworkLayer.ResponseDTOs.Building getBuilding() {
        return Building;
    }

    public void setBuilding(com.ets.gd.NetworkLayer.ResponseDTOs.Building building) {
        Building = building;
    }
}
