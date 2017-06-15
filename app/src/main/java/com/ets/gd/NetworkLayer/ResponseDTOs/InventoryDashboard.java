package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 5/23/2017.
 * General Data
 */

public class InventoryDashboard extends RealmObject {

 private ETSInventory ETSInventoryMaterialDetailsCount;

    public ETSInventory getETSInventoryMaterialDetailsCount() {
        return ETSInventoryMaterialDetailsCount;
    }

    public void setETSInventoryMaterialDetailsCount(ETSInventory ETSInventoryMaterialDetailsCount) {
        this.ETSInventoryMaterialDetailsCount = ETSInventoryMaterialDetailsCount;
    }
}
