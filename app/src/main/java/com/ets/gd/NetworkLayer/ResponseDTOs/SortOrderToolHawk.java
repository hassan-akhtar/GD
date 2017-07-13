package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

public class SortOrderToolHawk extends RealmObject {

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
