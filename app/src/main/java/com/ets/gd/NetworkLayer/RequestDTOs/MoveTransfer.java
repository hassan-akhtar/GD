package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class MoveTransfer {

    private String Action;
    private int EquipmentID;
    private int CustomerID;
    private int LocationID;


    public MoveTransfer(String action, int equipmentID, int customerID, int locationID) {
        Action = action;
        EquipmentID = equipmentID;
        CustomerID = customerID;
        LocationID = locationID;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int equipmentID) {
        EquipmentID = equipmentID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }

    public int getLocationID() {
        return LocationID;
    }

    public void setLocationID(int locationID) {
        LocationID = locationID;
    }
}
