package com.ets.gd.Models;

import io.realm.RealmObject;

/**
 * Created by hakhtar on 7/11/2017.
 * General Data
 */

public class RealmString extends RealmObject {
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
