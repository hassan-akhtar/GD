package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by hakhtar on 6/1/2017.
 * General Data
 */

public class Department extends RealmObject {
    @PrimaryKey
    private int ID;
    private String Code;
    private String Description;
    private int CustomerID;

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
}
