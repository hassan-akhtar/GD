package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

public class Stock extends RealmObject {

private int OutOfStockItems;
    private int LowAtStockItems;
    private int InStockItems;
    private int ReOrderItems;

    public int getOutOfStockItems() {
        return OutOfStockItems;
    }

    public void setOutOfStockItems(int outOfStockItems) {
        OutOfStockItems = outOfStockItems;
    }

    public int getLowAtStockItems() {
        return LowAtStockItems;
    }

    public void setLowAtStockItems(int lowAtStockItems) {
        LowAtStockItems = lowAtStockItems;
    }

    public int getInStockItems() {
        return InStockItems;
    }

    public void setInStockItems(int inStockItems) {
        InStockItems = inStockItems;
    }

    public int getReOrderItems() {
        return ReOrderItems;
    }

    public void setReOrderItems(int reOrderItems) {
        ReOrderItems = reOrderItems;
    }
}
