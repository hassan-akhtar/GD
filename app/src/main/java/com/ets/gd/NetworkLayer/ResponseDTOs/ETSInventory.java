package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 6/15/2017.
 * General Data
 */

public class ETSInventory extends RealmObject {

 private Stock Stock;

    public com.ets.gd.NetworkLayer.ResponseDTOs.Stock getStock() {
        return Stock;
    }

    public void setStock(com.ets.gd.NetworkLayer.ResponseDTOs.Stock stock) {
        Stock = stock;
    }
}

