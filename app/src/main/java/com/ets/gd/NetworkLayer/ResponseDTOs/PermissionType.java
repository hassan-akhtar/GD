package com.ets.gd.NetworkLayer.ResponseDTOs;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 7/11/2017.
 * General Data
 */

public class PermissionType extends RealmObject {

    private String type;

    public String getValue() {
        return type;
    }

    public void setValue(String type) {
        this.type = type;
    }
}
