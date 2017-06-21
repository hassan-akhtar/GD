package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/20/2017.
 * General Data
 */

public class InventoryMoveCall {

    public int MaterialID ;
    public String Code ;
    public int Quantity ;
    public int InventoryID ;

    public int getMaterialID() {
        return MaterialID;
    }

    public void setMaterialID(int materialID) {
        MaterialID = materialID;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public int getQuantity() {
        return Quantity;
    }

    public void setQuantity(int quantity) {
        Quantity = quantity;
    }

    public int getInventoryID() {
        return InventoryID;
    }

    public void setInventoryID(int inventoryID) {
        InventoryID = inventoryID;
    }
}
