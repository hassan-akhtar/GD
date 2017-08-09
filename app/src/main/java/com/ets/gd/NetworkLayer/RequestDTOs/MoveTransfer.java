package com.ets.gd.NetworkLayer.RequestDTOs;

/**
 * Created by hakhtar on 4/27/2017.
 * General Data
 */

public class MoveTransfer {

    private String Action;
    private String EquipmentCode;
    private int CustomerID;
    private int LocationID;
    private String LocationCode;


    public MoveTransfer(String action, String EqCode, int customerID, int locationID) {
        Action = action;
        EquipmentCode = EqCode;
        CustomerID = customerID;
        LocationID = locationID;
    }

    public MoveTransfer() {
    }

    public String getLocationCode() {
        return LocationCode;
    }

    public void setLocationCode(String locationCode) {
        LocationCode = locationCode;
    }

    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }

    public String getEquipmentID() {
        return EquipmentCode;
    }

    public void setEquipmentID(String EqCode) {
        EquipmentCode = EqCode;
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
