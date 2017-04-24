package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 4/13/2017.
 * General Data
 */

public class Locations extends RealmObject {

    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private String Floor;
    private String Room;
    private Customer Customer;
    private Site Site;
    private Building Building;

    public Locations(String code, String description, com.ets.gd.NetworkLayer.ResponseDTOs.Customer customer, com.ets.gd.NetworkLayer.ResponseDTOs.Site site, com.ets.gd.NetworkLayer.ResponseDTOs.Building building) {
        Code = code;
        Description = description;
        Customer = customer;
        Site = site;
        Building = building;
    }

    public Locations() {

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
