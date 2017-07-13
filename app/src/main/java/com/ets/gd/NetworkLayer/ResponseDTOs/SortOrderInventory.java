package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 7/13/2017.
 * General Data
 */

public class SortOrderInventory extends RealmObject {

    private String MenuName;
    private String SortOrder;

    public String getName() {
        return MenuName;
    }

    public void setName(String name) {
        this.MenuName = name;
    }

    public String getSortOrder() {
        return SortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.SortOrder = sortOrder;
    }
}
